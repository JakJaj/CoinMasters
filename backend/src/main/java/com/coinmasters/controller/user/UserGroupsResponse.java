package com.coinmasters.controller.user;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGroupsResponse {
    private Set<GroupInfo> userGroups;
}
