package com.coinmasters.controller.group;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeGroupDetailsRequest {
    private String newGroupName;
    private String newGoal;
    private String newCurrency;
}
