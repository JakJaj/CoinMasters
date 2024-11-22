package com.coinmasters.controller.transactions;

import com.coinmasters.dto.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupTransactionResponse {

    private List<TransactionDTO> transactions;
}
