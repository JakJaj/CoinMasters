package com.coinmasters.service;

import com.coinmasters.config.JwtService;
import com.coinmasters.controller.auth.SecurityUtils;
import com.coinmasters.controller.user.*;
import com.coinmasters.dao.UserRepository;
import com.coinmasters.dto.GroupDTO;
import com.coinmasters.entity.Role;
import com.coinmasters.entity.User;
import com.coinmasters.exceptions.IncorrectPasswordException;
import com.coinmasters.exceptions.NoSuchUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserDetails() {
        String token = "Bearer some.jwt.token";
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setUserId(1L);
        user.setName("Test User");
        user.setRolee(Role.USER);

        when(jwtService.extractUsername(token.substring(7))).thenReturn(email);
        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetailsResponse response = userService.getUserDetails(token);

        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        assertEquals("Test User", response.getName());
        assertEquals(email, response.getMail());
        assertEquals("USER", response.getRolee());
    }

    @Test
    void testGetUserDetails_UserNotFound() {
        String token = "Bearer some.jwt.token";
        String email = "test@example.com";

        when(jwtService.extractUsername(token.substring(7))).thenReturn(email);
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> userService.getUserDetails(token));
    }

    @Test
    void testGetGroupsOfUser() {
        String token = "Bearer some.jwt.token";
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setUserGroups(Set.of());

        when(jwtService.extractUsername(token.substring(7))).thenReturn(email);
        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        UserGroupsResponse response = userService.getGroupsOfUser(token);

        assertNotNull(response);
        assertTrue(response.getUserGroups().isEmpty());
    }

    @Test
    void testGetGroupsOfUser_UserNotFound() {
        String token = "Bearer some.jwt.token";
        String email = "test@example.com";

        when(jwtService.extractUsername(token.substring(7))).thenReturn(email);
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> userService.getGroupsOfUser(token));
    }

    @Test
    void testChangePassword() {
        String token = "Bearer some.jwt.token";
        String email = "test@example.com";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String salt = "salt";
        User user = new User();
        user.setEmail(email);
        user.setPasswordSalt(salt);
        user.setPassword(passwordEncoder.encode(salt + oldPassword));

        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword(oldPassword);
        request.setNewPassword(newPassword);

        when(jwtService.extractUsername(token.substring(7))).thenReturn(email);
        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(salt + oldPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(salt + newPassword, user.getPassword())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPassword");

        ChangePasswordResponse response = userService.changePassword(request, token);

        assertNotNull(response);
        assertEquals("Success", response.getStatus());
        assertEquals("Password changed successfully", response.getMessage());
        verify(repository).save(user);
    }

    @Test
    void testChangePassword_IncorrectOldPassword() {
        String token = "Bearer some.jwt.token";
        String email = "test@example.com";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String salt = "salt";
        User user = new User();
        user.setEmail(email);
        user.setPasswordSalt(salt);
        user.setPassword(passwordEncoder.encode(salt + oldPassword));

        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword(oldPassword);
        request.setNewPassword(newPassword);

        when(jwtService.extractUsername(token.substring(7))).thenReturn(email);
        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(salt + oldPassword, user.getPassword())).thenReturn(false);

        assertThrows(IncorrectPasswordException.class, () -> userService.changePassword(request, token));
    }

    @Test
    void testChangePassword_SameAsOldPassword() {
        String token = "Bearer some.jwt.token";
        String email = "test@example.com";
        String oldPassword = "oldPassword";
        String newPassword = "oldPassword";
        String salt = "salt";
        User user = new User();
        user.setEmail(email);
        user.setPasswordSalt(salt);
        user.setPassword(passwordEncoder.encode(salt + oldPassword));

        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword(oldPassword);
        request.setNewPassword(newPassword);

        when(jwtService.extractUsername(token.substring(7))).thenReturn(email);
        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(salt + oldPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(salt + newPassword, user.getPassword())).thenReturn(true);

        assertThrows(IncorrectPasswordException.class, () -> userService.changePassword(request, token));
    }
}
