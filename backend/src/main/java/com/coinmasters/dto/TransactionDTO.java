package com.coinmasters.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO {
    private Long transactionId;
    private String name;
    private String category;
    private String date;
    private String creatorName;
}
