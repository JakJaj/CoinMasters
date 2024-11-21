package com.coinmasters.controller.user;

import com.coinmasters.exceptions.NoSuchUserException;
import com.coinmasters.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getUserDetails(token));
    }

    @GetMapping("/groups")
    public ResponseEntity<UserGroupsResponse> getGroupsOfUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getGroupsOfUser(token));
    }
}
