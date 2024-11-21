package com.coinmasters.service;

import com.coinmasters.controller.user.GroupInfo;
import com.coinmasters.controller.user.UserDetailsResponse;
import com.coinmasters.controller.user.UserGroupsResponse;
import com.coinmasters.dao.UserRepository;
import com.coinmasters.entity.User;
import com.coinmasters.entity.UserGroup.UserGroup;
import com.coinmasters.exceptions.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserDetailsResponse getUserByUserId(Long id){
        Optional<User> user = repository.findByUserId(id);
        if (user.isPresent()){
            User existingUser = user.get();
            return UserDetailsResponse.builder()
                    .userId(existingUser.getUserId())
                    .name(existingUser.getName())
                    .mail(existingUser.getEmail())
                    .rolee(existingUser.getRolee().toString())
                    .build();
        }
        throw new NoSuchUserException(String.format("No user with id - %d", id));
    }

    public UserGroupsResponse getGroupsOfUser(Long id){
        return repository.findByUserId(id)
                .map(existingUser -> UserGroupsResponse.builder()
                        .userGroups(existingUser.getUserGroups().stream()
                                .map(group -> GroupInfo.builder()
                                        .groupId(group.getGroup().getGroupId())
                                        .groupName(group.getGroup().getGroupName())
                                        .build())
                                .collect(Collectors.toSet()))
                        .build())
                .orElseThrow(() -> new NoSuchUserException(String.format("No user with id - %d", id)));
    }
}
