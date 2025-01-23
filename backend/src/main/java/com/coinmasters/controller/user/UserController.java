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
    @CrossOrigin
    public ResponseEntity<UserDetailsResponse> getUserDetails(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getUserDetails(token));
    }

    /**
     *
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @return Set of groups that a currently logged-in user is a part of.
     */
    @GetMapping("/groups")
    @CrossOrigin
    public ResponseEntity<UserGroupsResponse> getGroupsOfUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getGroupsOfUser(token));
    }

    /**
     * Endpoint for changing user's password.
     * The Password will not be changed if the old password doesn't match the provided old password
     * or when a new password is the same as the old one.
     * @param request The {@link ChangePasswordRequest} schema request.
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @return The {@link ChangePasswordResponse} schema that is presenting whether a password was changes or if something went wrong.
     */
    @PutMapping("/password")
    @CrossOrigin
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.changePassword(request, token));
    }
}
