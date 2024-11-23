package com.coinmasters.service;

import com.coinmasters.config.JwtService;
import com.coinmasters.controller.group.JoinGroupRequest;
import com.coinmasters.controller.group.JoinGroupResponse;
import com.coinmasters.controller.user.GroupInfo;
import com.coinmasters.dao.GroupRepository;
import com.coinmasters.dao.UserGroupRepository;
import com.coinmasters.dao.UserRepository;
import com.coinmasters.entity.User;
import com.coinmasters.entity.UserGroup.UserGroup;
import com.coinmasters.entity.UserGroup.UserGroupId;
import com.coinmasters.exceptions.CannotJoinGroupException;
import com.coinmasters.exceptions.NoSuchGroupException;
import com.coinmasters.exceptions.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.coinmasters.entity.Group;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

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
}
