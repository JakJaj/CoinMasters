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

    @PostMapping("/users")
    public ResponseEntity<JoinGroupResponse> joinGroupUsingCode(@RequestBody JoinGroupRequest request, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(groupService.joinGroup(request, token));
    }
}
