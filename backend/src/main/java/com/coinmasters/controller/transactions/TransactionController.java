package com.coinmasters.controller.transactions;


import com.coinmasters.dto.TransactionDTO;
import com.coinmasters.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {


    private final TransactionService transactionService;


    /**
     *
     * @param groupID id of the group that you want a transactions from
     * @return a list of transactions from a specified group with details
     */
    @GetMapping("/groups/{groupID}")
    public ResponseEntity<GroupTransactionResponse> getGroupsTransactions(@PathVariable Long groupID){
        return ResponseEntity.ok(transactionService.getTransactionOfGroup(groupID));
    }

    /**
     *
     * @param transactionID id of the transaction that you want the details of
     * @return transaction details such as id, name, category, date and creator name
     */
    @GetMapping("/{transactionID}")
    public ResponseEntity<TransactionDTO> getTransactionDetails(@PathVariable Long transactionID){
        return ResponseEntity.ok(transactionService.getTransactionDetails(transactionID));
    }
}