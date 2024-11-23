package com.coinmasters.controller.group;

import com.coinmasters.controller.user.GroupInfo;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class JoinGroupResponse {
    private String status;
    private String message;
    private GroupInfo group;
}
