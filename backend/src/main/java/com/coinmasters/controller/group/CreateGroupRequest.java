package com.coinmasters.controller.group;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateGroupRequest {
    private String groupName;
    private String goal;
    private String currency;
}
