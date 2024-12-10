package com.coinmasters.controller.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinGroupRequest {
    private String joinCode;
}
