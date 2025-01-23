package com.coinmasters.service;

import com.coinmasters.config.JwtService;
import com.coinmasters.controller.auth.SecurityUtils;
import com.coinmasters.controller.user.*;
import com.coinmasters.dao.GroupRepository;
import com.coinmasters.dao.UserRepository;
import com.coinmasters.dto.GroupDTO;
import com.coinmasters.entity.Group;
import com.coinmasters.entity.User;
import com.coinmasters.exceptions.IncorrectPasswordException;
import com.coinmasters.exceptions.NoSuchUserException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final GroupRepository groupRepository;

    public UserDetailsResponse getUserDetails(String token){
        String email = jwtService.extractUsername(token.substring(7));
        System.out.println(email);
        Optional<User> user = repository.findByEmail(email);

        if (user.isPresent()){
        Optional<Set<Group>> adminOfGroupsOptional = groupRepository.getGroupsByAdminUserId_UserId(user.get().getUserId());
        Set<Long> adminOfGroups = adminOfGroupsOptional.map(groups -> groups.stream()
                .map(Group::getGroupId)
                .collect(Collectors.toSet()))
                .orElse(Set.of());

        User existingUser = user.get();
        return UserDetailsResponse.builder()
                .userId(existingUser.getUserId())
                .name(existingUser.getName())
                .mail(existingUser.getEmail())
                .rolee(existingUser.getRolee().toString())
                .adminOfGroups(adminOfGroups)
                .build();
        }
        throw new NoSuchUserException("This should never happen. If it did then, like, damn...");
    }

    public UserGroupsResponse getGroupsOfUser(String token){
        String email = jwtService.extractUsername(token.substring(7));
        return repository.findByEmail(email)
                .map(existingUser -> UserGroupsResponse.builder()
                        .userGroups(existingUser.getUserGroups().stream()
                                .map(group -> GroupDTO.builder()
                                        .groupId(group.getGroup().getGroupId())
                                        .groupName(group.getGroup().getGroupName())
                                        .goal(group.getGroup().getGoal())
                                        .currency(group.getGroup().getCurrency())
                                        .joinCode(group.getGroup().getJoinCode())
                                        .build()
                                ).collect(Collectors.toSet()))
                        .build())
                .orElseThrow(() -> new NoSuchUserException("This should never happen. If it did then, like, damn..."));
    }

    @Transactional
    public ChangePasswordResponse changePassword(ChangePasswordRequest request, String token) {

        String email = jwtService.extractUsername(token.substring(7));
        User user = repository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("Can't happen. If it did then idk how"));

        if (!passwordEncoder.matches(user.getPasswordSalt() + request.getOldPassword(), user.getPassword())){
            throw new IncorrectPasswordException("Wrong old password");
        }

        if (passwordEncoder.matches(user.getPasswordSalt() + request.getNewPassword(), user.getPassword())){
            throw new IncorrectPasswordException("New password is the same as the old one");
        }


        String newSalt = SecurityUtils.generateSalt();

        user.setPassword(passwordEncoder.encode(newSalt + request.getNewPassword()));
        user.setPasswordSalt(newSalt);

        repository.save(user);
        return ChangePasswordResponse.builder()
                .status("Success")
                .message("Password changed successfully")
                .build();
    }
}
