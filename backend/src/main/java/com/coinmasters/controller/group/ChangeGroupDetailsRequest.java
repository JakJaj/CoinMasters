package com.coinmasters.controller.group;

import lombok.Data;

@Data
public class ChangeGroupDetailsRequest {
    private String newGroupName;
    private String newGoal;
    private String newCurrency;
}
