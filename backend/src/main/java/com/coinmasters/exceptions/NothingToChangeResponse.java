package com.coinmasters.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NothingToChangeResponse {
    private String status;
    private String message;
}
