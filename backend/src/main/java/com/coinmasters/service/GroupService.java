package com.coinmasters.service;

import com.coinmasters.config.JwtService;
import com.coinmasters.controller.group.*;
import com.coinmasters.controller.user.UserDetailsResponse;
import com.coinmasters.dao.TransactionRepository;
import com.coinmasters.dto.GroupDTO;
import com.coinmasters.dao.GroupRepository;
import com.coinmasters.dao.UserGroupRepository;
import com.coinmasters.dao.UserRepository;
import com.coinmasters.entity.User;
import com.coinmasters.entity.UserGroup.UserGroup;
import com.coinmasters.entity.UserGroup.UserGroupId;
import com.coinmasters.exceptions.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.coinmasters.entity.Group;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final TransactionRepository transactionRepository;
    private final JwtService jwtService;

    public JoinGroupResponse joinGroup(JoinGroupRequest request, String token) {

        String joinCode = request.getJoinCode();
        String email = jwtService.extractUsername(token.substring(7));

        User user = userRepository.findByEmail((email)).orElseThrow(() -> new NoSuchUserException("It just really can't happen. But if it did then something is wrong."));

        Group group = groupRepository.getGroupByJoinCode(joinCode).orElseThrow(() -> new CannotJoinGroupException("Wrong join code provided",
                JoinGroupResponse.builder()
                        .status("Failure")
                        .message("Incorrect join code")
                        .group(null)
                        .build(),
                HttpStatus.NOT_FOUND));

        if (userGroupRepository.existsById(new UserGroupId(user.getUserId(), group.getGroupId()))){
            throw new CannotJoinGroupException("Already a part of this group",
                    JoinGroupResponse.builder()
                            .status("Conflict")
                            .message("Already a part of this group")
                            .group(null)
                            .build(),
                    HttpStatus.CONFLICT
            );
        }

        UserGroup userGroup = userGroupRepository.save(
                UserGroup.builder()
                        .id(new UserGroupId(user.getUserId(), group.getGroupId()))
                        .user(user)
                        .group(group)
                        .joinDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()))
                        .build()
        );

        user.getUserGroups().add(userGroup);
        group.getUserGroups().add(userGroup);

        return JoinGroupResponse.builder()
                .status("Success")
                .message("User added to a group")
                .group(GroupDTO.builder()
                        .groupId(group.getGroupId())
                        .groupName(group.getGroupName())
                        .goal(group.getGoal())
                        .currency(group.getCurrency())
                        .build())
                .build();
    }

    public CreateGroupResponse createGroup(CreateGroupRequest request, String token) {

        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("It just really can't happen. But if it did then something is wrong."));

        Group group = groupRepository.save(Group.builder()
                .groupName(request.getGroupName())
                .goal(request.getGoal())
                .currency(request.getCurrency())
                .joinCode(GroupUtils.crateJoinCode())
                .userGroups(new HashSet<>())
                .adminUserId(user)
                .build());

        UserGroup userGroup = userGroupRepository.save(
                UserGroup.builder()
                        .id(new UserGroupId(user.getUserId(), group.getGroupId()))
                        .user(user)
                        .group(group)
                        .joinDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()))
                        .build()
        );

        group.getUserGroups().add(userGroup);
        user.getUserGroups().add(userGroup);

        return CreateGroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .goal(group.getGoal())
                .currency(group.getCurrency())
                .joinCode(group.getJoinCode())
                .creatorName(user.getName())
                .build();
    }

    @Transactional
    public DeleteGroupResponse deleteGroup(Long groupID, String token) {

        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("It just really can't happen. But if it did then something is wrong."));

        Group group = groupRepository.getGroupByGroupId(groupID).orElseThrow(() -> new NoSuchGroupException(String.format("No group with id - %s", groupID)));

        if (group.getAdminUserId().getUserId().longValue() == user.getUserId().longValue()){
            transactionRepository.deleteTransactionsByGroup_GroupId(groupID);
            userGroupRepository.deleteByGroup_GroupId(groupID);
            groupRepository.delete(group);
            return DeleteGroupResponse.builder()
                    .status("Deleted")
                    .message(String.format("Group with id - %d was deleted", group.getGroupId()))
                    .build();
        }else {
            throw new ActionPerformedByNonAdminUserException("Delete action performed by an non-admin user");
        }
    }


    @Transactional
    public ChangeGroupDetailsResponse changeGroupDetails(Long groupID, ChangeGroupDetailsRequest request, String token) {

        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("It just rally can't happen. If it did then, like, damn..."));

        Group group = groupRepository.getGroupByGroupId(groupID).orElseThrow(() -> new NoSuchGroupException(String.format("No group with id - %s", groupID)));

        if (request.getNewGoal() == null && request.getNewCurrency() == null && request.getNewGroupName() == null) throw new NothingToChangeException("All data is null. Something went wrong while passing data in");
        if (group.getAdminUserId().getUserId().equals(user.getUserId())){

            if (request.getNewGroupName() != null && !request.getNewGroupName().equals(group.getGroupName())) {
                group.setGroupName(request.getNewGroupName());
            }

            if (request.getNewCurrency() != null && !request.getNewCurrency().equals(group.getCurrency())) {
                group.setCurrency(request.getNewCurrency());
            }

            if (request.getNewGoal() != null && !request.getNewGoal().equals(group.getGoal())) {
                group.setGoal(request.getNewGoal());
            }

            Group savedGroup = groupRepository.save(group);

            return ChangeGroupDetailsResponse.builder()
                    .status("Changed")
                    .message("Details changed")
                    .group(GroupDTO.builder()
                            .groupId(savedGroup.getGroupId())
                            .groupName(savedGroup.getGroupName())
                            .goal(savedGroup.getGoal())
                            .currency(savedGroup.getCurrency())
                            .build())
                    .build();
        }
        else {
            throw new ActionPerformedByNonAdminUserException("Group data change performed by non admin user");
        }

    }

    public GroupUsersResponse getAllUsersFromGroup(Long groupID, String token) {
        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("It just rally can't happen. If it did then, like, damn..."));

        Group group = groupRepository.getGroupByGroupId(groupID).orElseThrow(() -> new NoSuchGroupException(String.format("No group with id - %s", groupID)));

        boolean isUserInGroup = user.getUserGroups().stream()
                .anyMatch(userGroup -> userGroup.getGroup().getGroupId().equals(groupID));

        if (!isUserInGroup){
            throw new UserNotAMemberOfTheGroupException("User isn't a part of a specified group");
        }
        Set<User> groupUsers = group.getUserGroups().stream()
                .map(UserGroup::getUser)
                .collect(Collectors.toSet());

        List<UserDetailsResponse> users = groupUsers.stream()
                .map(u -> new UserDetailsResponse(u.getUserId(), u.getName(), u.getEmail(), u.getRolee().toString()))
                .toList();

        return GroupUsersResponse.builder()
                .status("Success")
                .message("Returned list of users")
                .groupMembers(users)
                .build();
    }


    @Transactional
    public RemoveUserResponse removeSelfFromGroup(Long groupID, String token) {
        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("It just rally can't happen. If it did then, like, damn..."));

        Group group = groupRepository.getGroupByGroupId(groupID).orElseThrow(() -> new NoSuchGroupException(String.format("No group with id - %s", groupID)));

        boolean isUserInGroup = user.getUserGroups().stream()
                .anyMatch(userGroup -> userGroup.getGroup().getGroupId().equals(groupID));

        if (!isUserInGroup){
            throw new UserNotAMemberOfTheGroupException("User isn't a part of a specified group");
        }

        userGroupRepository.deleteByGroup_GroupIdAndUser_UserId(group.getGroupId(),user.getUserId());

        return RemoveUserResponse.builder()
                .status("Success")
                .message("User removed self from group")
                .build();
    }

    @Transactional
    public RemoveUserResponse removeUserFromGroup(Long groupID, Long userToDeleteID, String token) {
        String email = jwtService.extractUsername(token.substring(7));
        User userAdmin = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("It just rally can't happen. If it did then, like, damn..."));

        Group group = groupRepository.getGroupByGroupId(groupID).orElseThrow(() -> new NoSuchGroupException(String.format("No group with id - %s", groupID)));

        if (!userAdmin.getUserId().equals(group.getAdminUserId().getUserId())){
            throw new ActionPerformedByNonAdminUserException("Action performed by a non admin user");
        }

        User userToDelete = userRepository.findByUserId(userToDeleteID).orElseThrow(() -> new NoSuchUserException("User doesn't exist"));

        if (userToDelete.getUserId().equals(group.getAdminUserId().getUserId())){
            throw new AdminDeletingException("Admin can't be removed from the group");
        }

        for (UserGroup userGroup: userToDelete.getUserGroups()){
            if (userGroup.getGroup().getGroupId().equals(group.getGroupId())){
                userGroupRepository.deleteByGroup_GroupIdAndUser_UserId(groupID, userToDeleteID);
            }
        }

        return RemoveUserResponse.builder()
                .status("Success")
                .message("User removed")
                .build();
    }

}
