package com.coinmasters.controller.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordResponse {
    private String status;
    private String message;
}
