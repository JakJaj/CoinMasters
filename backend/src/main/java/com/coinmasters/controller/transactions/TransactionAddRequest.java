package com.coinmasters.controller.transactions;

import lombok.Data;

@Data
public class TransactionAddRequest {
    private String name;
    private String category;
    private String date;
    private float amount;
    private Long groupId;
}
