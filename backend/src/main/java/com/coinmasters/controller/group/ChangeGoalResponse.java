package com.coinmasters.controller.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeGoalResponse {
    private String status;
    private String message;
}
