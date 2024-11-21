package com.coinmasters.service;

import com.coinmasters.controller.user.UserDetailsResponse;
import com.coinmasters.dao.UserRepository;
import com.coinmasters.entity.User;
import com.coinmasters.exceptions.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserDetailsResponse getUserByUserId(Long id) throws NoSuchUserException {
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
}
