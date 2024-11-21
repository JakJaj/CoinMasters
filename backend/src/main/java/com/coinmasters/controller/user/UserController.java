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


    /**
     *
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @return User details that contains information about user such as id, email, name, role
     */
    @GetMapping("")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getUserDetails(token));
    }

    /**
     *
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @return Set of groups that a currently logged-in user is a part of.
     */
    @GetMapping("/groups")
    public ResponseEntity<UserGroupsResponse> getGroupsOfUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getGroupsOfUser(token));
    }
}
