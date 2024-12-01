package com.coinmasters.controller.transactions;

import lombok.Data;

@Data
public class ChangeTransactionDetailsRequest {
    private String newName;
    private String newCategory;
    private String newDate;
    private float newAmount;
}
