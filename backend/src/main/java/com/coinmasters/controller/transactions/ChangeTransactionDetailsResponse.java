package com.coinmasters.controller.transactions;

import com.coinmasters.dto.TransactionDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeTransactionDetailsResponse {
    private String status;
    private String message;
    private TransactionDTO transaction;
}
