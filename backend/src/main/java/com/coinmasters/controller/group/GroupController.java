package com.coinmasters.controller.group;

import com.coinmasters.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    /**
     *  Endpoint for user to join a group u needs to specify a join code using {@link JoinGroupRequest} schema.
     *  A user that needs to be added to a group will be taken from jwt token data.
     * @param request Schema for providing join code see - {@link JoinGroupRequest}
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @return Response that is using schema {@link  JoinGroupResponse}. You will get details about whether the user was added, and if it was successful, then you will also get data about the group that the user joined.
     */
    @PostMapping("/users")
    public ResponseEntity<JoinGroupResponse> joinGroupUsingCode(@RequestBody JoinGroupRequest request, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(groupService.joinGroup(request, token));
    }

    /**
     * Endpoint for user to create a group u needs to specify a group details using {@link CreateGroupRequest} schema.
     * A user that needs to be added to a group will be taken from jwt token data.
     * @param request Schema for providing group details see - {@link CreateGroupRequest}
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @return Response that is using schema {@link  CreateGroupResponse}. You will get details about a database that was created.
     */
    @PostMapping("")
    public ResponseEntity<CreateGroupResponse> createGroup(@RequestBody CreateGroupRequest request, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(groupService.createGroup(request, token));
    }
}
