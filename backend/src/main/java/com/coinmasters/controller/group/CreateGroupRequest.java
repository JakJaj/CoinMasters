package com.coinmasters.controller.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class CreateGroupRequest {
    private String groupName;
    private String goal;
    private String currency;
}
