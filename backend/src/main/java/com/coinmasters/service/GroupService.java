package com.coinmasters.service;

import com.coinmasters.config.JwtService;
import com.coinmasters.controller.group.*;
import com.coinmasters.controller.user.GroupInfo;
import com.coinmasters.dao.GroupRepository;
import com.coinmasters.dao.UserGroupRepository;
import com.coinmasters.dao.UserRepository;
import com.coinmasters.entity.User;
import com.coinmasters.entity.UserGroup.UserGroup;
import com.coinmasters.entity.UserGroup.UserGroupId;
import com.coinmasters.exceptions.CannotJoinGroupException;
import com.coinmasters.exceptions.GroupDeletionByNonAdminUserException;
import com.coinmasters.exceptions.NoSuchGroupException;
import com.coinmasters.exceptions.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.coinmasters.entity.Group;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
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
                .group(GroupInfo.builder()
                        .groupId(group.getGroupId())
                        .groupName(group.getGroupName())
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

    public DeleteGroupResponse deleteGroup(Long groupID, String token) {

        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("It just really can't happen. But if it did then something is wrong."));

        Group group = groupRepository.getGroupByGroupId(groupID).orElseThrow(() -> new NoSuchGroupException(String.format("No group with id - %s", groupID)));

        if (group.getAdminUserId().getUserId().longValue() == user.getUserId().longValue()){
            groupRepository.delete(group);
            return DeleteGroupResponse.builder()
                    .status("Deleted")
                    .message(String.format("Group with id - %d was deleted", group.getGroupId()))
                    .build();
        }else {
            throw new GroupDeletionByNonAdminUserException("Delete action performed by an non-admin user");
        }
    }
}
