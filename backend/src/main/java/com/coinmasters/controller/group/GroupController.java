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
     * Endpoint for getting a list of all users that are a member of a specified group
     * @param groupID id of the group that you want to get users of
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @return Response that is using schema {@link GroupUsersResponse}.
     * You will get details about every user that is a part of a group including the user that is calling this function
     */
    @GetMapping("/{groupID}/users")
    public ResponseEntity<GroupUsersResponse> getAllUsersFromGroup(@PathVariable Long groupID, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(groupService.getAllUsersFromGroup(groupID, token));
    }
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

    /**
     * Endpoint for user to delete a group u need to specify a groupID as a path variable.
     * @param groupID A path variable that you need to specify in URL
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @return Response that is using schema {@link DeleteGroupResponse}. You will get status about a group being deleted or not.
     */
    @DeleteMapping("/{groupID}")
    public ResponseEntity<DeleteGroupResponse> deleteGroup(@PathVariable Long groupID, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(groupService.deleteGroup(groupID, token));
    }

    /**
     * Endpoint for changing details of a group.
     * This can only be performed by a group admin.
     * Otherwise, it will return the {@link com.coinmasters.exceptions.ActionPerformedByNonAdminUserException}.
     * @param groupID path variable that indicates what group details will be changed.
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @param request request that is using {@link ChangeGroupDetailsRequest} schema. It can take at least one detail to change.
     *                Otherwise, it will throw {@link com.coinmasters.exceptions.NothingToChangeException}
     * @return Response using {@link ChangeGroupDetailsResponse} schema. That contains status, message and new group details.
     */
    @PutMapping("/{groupID}")
    private ResponseEntity<ChangeGroupDetailsResponse> changeGroupDetails(@PathVariable Long groupID, @RequestHeader("Authorization") String token, @RequestBody ChangeGroupDetailsRequest request){
        return ResponseEntity.ok(groupService.changeGroupDetails(groupID, request, token));
    }
}
