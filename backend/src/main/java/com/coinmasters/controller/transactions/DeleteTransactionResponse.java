package com.coinmasters.controller.transactions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteTransactionResponse {
    private String status;
    private String message;
}
