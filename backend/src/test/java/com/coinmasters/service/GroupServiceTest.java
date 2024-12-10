package com.coinmasters.service;

import com.coinmasters.config.JwtService;
import com.coinmasters.controller.group.*;
import com.coinmasters.dao.*;
import com.coinmasters.entity.Group;
import com.coinmasters.entity.User;
import com.coinmasters.entity.UserGroup.UserGroup;
import com.coinmasters.entity.UserGroup.UserGroupId;
import com.coinmasters.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserGroupRepository userGroupRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteGroup_Success() {
        String token = "Bearer token";
        String email = "test@example.com";
        Long groupId = 1L;

        User user = new User();
        user.setUserId(1L);
        user.setEmail(email);

        Group group = new Group();
        group.setGroupId(groupId);
        group.setAdminUserId(user);

        when(jwtService.extractUsername(token.substring(7))).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(groupRepository.getGroupByGroupId(groupId)).thenReturn(Optional.of(group));

        DeleteGroupResponse response = groupService.deleteGroup(groupId, token);

        verify(groupRepository, times(1)).delete(group);
        assertEquals("Deleted", response.getStatus());
        assertEquals(String.format("Group with id - %d was deleted", groupId), response.getMessage());
    }

    @Test
    void testChangeGroupDetails_Success() {
        String token = "Bearer token";
        String email = "test@example.com";
        Long groupId = 1L;
        ChangeGroupDetailsRequest request = new ChangeGroupDetailsRequest("NewGroupName", "NewGoal", "NewCurrency");

        User user = new User();
        user.setUserId(1L);
        user.setEmail(email);

        Group group = new Group();
        group.setGroupId(groupId);
        group.setAdminUserId(user);

        when(jwtService.extractUsername(token.substring(7))).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(groupRepository.getGroupByGroupId(groupId)).thenReturn(Optional.of(group));
        when(groupRepository.save(any(Group.class))).thenReturn(group);

        ChangeGroupDetailsResponse response = groupService.changeGroupDetails(groupId, request, token);

        assertEquals("Changed", response.getStatus());
        assertEquals("Details changed", response.getMessage());
        assertNotNull(response.getGroup());
    }

    @Test
    void testRemoveSelfFromGroup_Success() {
        String token = "Bearer token";
        String email = "test@example.com";
        Long groupId = 1L;

        User user = new User();
        user.setUserId(1L);
        user.setEmail(email);

        Group group = new Group();
        group.setGroupId(groupId);

        UserGroup userGroup = new UserGroup();
        userGroup.setUser(user);
        userGroup.setGroup(group);

        user.setUserGroups(Set.of(userGroup));
        group.setUserGroups(Set.of(userGroup));

        when(jwtService.extractUsername(token.substring(7))).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(groupRepository.getGroupByGroupId(groupId)).thenReturn(Optional.of(group));

        RemoveUserResponse response = groupService.removeSelfFromGroup(groupId, token);

        verify(userGroupRepository, times(1)).deleteByGroup_GroupIdAndUser_UserId(groupId, user.getUserId());
        assertEquals("Success", response.getStatus());
        assertEquals("User removed self from group", response.getMessage());
    }

    @Test
    void testRemoveUserFromGroup_Success() {
        String token = "Bearer token";
        String email = "test@example.com";
        Long groupId = 1L;
        Long userToDeleteId = 2L;

        User adminUser = new User();
        adminUser.setUserId(1L);
        adminUser.setEmail(email);

        User userToDelete = new User();
        userToDelete.setUserId(userToDeleteId);

        Group group = new Group();
        group.setGroupId(groupId);
        group.setAdminUserId(adminUser);

        UserGroup userGroup = new UserGroup();
        userGroup.setUser(userToDelete);
        userGroup.setGroup(group);

        userToDelete.setUserGroups(Set.of(userGroup));
        group.setUserGroups(Set.of(userGroup));

        when(jwtService.extractUsername(token.substring(7))).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(adminUser));
        when(groupRepository.getGroupByGroupId(groupId)).thenReturn(Optional.of(group));
        when(userRepository.findByUserId(userToDeleteId)).thenReturn(Optional.of(userToDelete));

        RemoveUserResponse response = groupService.removeUserFromGroup(groupId, userToDeleteId, token);

        verify(userGroupRepository, times(1)).deleteByGroup_GroupIdAndUser_UserId(groupId, userToDeleteId);
        assertEquals("Success", response.getStatus());
        assertEquals("User removed", response.getMessage());
    }
}
