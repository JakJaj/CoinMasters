package com.coinmasters.controller.group;

import com.coinmasters.controller.user.UserDetailsResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupUsersResponse {
    private String status;
    private String message;
    private List<UserDetailsResponse> groupMembers;
}
