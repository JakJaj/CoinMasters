package com.coinmasters.controller.user;

import com.coinmasters.dto.GroupDTO;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGroupsResponse {
    private Set<GroupDTO> userGroups;
}
