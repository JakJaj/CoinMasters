package com.coinmasters.controller.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGroupResponse {
    private Long groupId;
    private String groupName;
    private String goal;
    private String currency;
    private String joinCode;
    private String creatorName;
}
