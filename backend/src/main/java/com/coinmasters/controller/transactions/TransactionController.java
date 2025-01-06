package com.coinmasters.controller.transactions;


import com.coinmasters.controller.group.ChangeGroupDetailsResponse;
import com.coinmasters.dto.TransactionDTO;
import com.coinmasters.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @CrossOrigin
    public ResponseEntity<GroupTransactionResponse> getGroupsTransactions(@PathVariable Long groupID){
        return ResponseEntity.ok(transactionService.getTransactionOfGroup(groupID));
    }

    /**
     *
     * @param transactionID id of the transaction that you want the details of
     * @return transaction details such as id, name, category, date and creator name
     */
    @GetMapping("/{transactionID}")
    @CrossOrigin
    public ResponseEntity<TransactionDTO> getTransactionDetails(@PathVariable Long transactionID){
        return ResponseEntity.ok(transactionService.getTransactionDetails(transactionID));
    }

    /**
     *
     * @param request transaction details that follow the {@link TransactionAddRequest} schema
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @return created transaction details 
     */
    @PostMapping("")
    @CrossOrigin
    public ResponseEntity<TransactionDTO> addNewTransaction(@RequestBody TransactionAddRequest request, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(transactionService.addTransaction(request, token));
    }

    /**
     * Endpoint for deleting a transaction.
     * Based on the current implementation,
     * a transaction can be deleted by anyone that is a member of a group that this transaction belongs to.
     * @param transactionID id of the transaction that you want to delete
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @return response following {@link DeleteTransactionResponse} schema. Containing status about deletion of a transaction process.
     */
    @DeleteMapping("/{transactionID}")
    @CrossOrigin
    public ResponseEntity<DeleteTransactionResponse> deleteTransaction(@PathVariable Long transactionID, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(transactionService.deleteTransaction(transactionID, token));
    }

    /**
     * Endpoint for changing transaction detail.
     * Based on this implementation, the user modifying the transactions becomes a new creator.
     * Remember date format YYYY-MM-DD.
     * @param transactionID id of transaction that you want to change details of
     * @param request request body following {@link ChangeTransactionDetailsRequest} schema.
     *                It can take at least one detail to change.
     *                Otherwise, it will throw {@link com.coinmasters.exceptions.NothingToChangeException}
     * @param token this token is provided in an Authorization header without (you will get it after logging in)
     * @return Response using {@link ChangeTransactionDetailsResponse} schema.
     * That contains status, message and new transaction details.
     */
    @PatchMapping("/{transactionID}")
    @CrossOrigin
    public ResponseEntity<ChangeTransactionDetailsResponse> changeTransactionDetails(@PathVariable Long transactionID, @RequestBody ChangeTransactionDetailsRequest request, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(transactionService.changeTransactionDetails(transactionID, request, token));
    }
}