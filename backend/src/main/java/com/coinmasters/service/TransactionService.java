package com.coinmasters.service;


import com.coinmasters.controller.transactions.GroupTransactionResponse;
import com.coinmasters.dao.TransactionRepository;
import com.coinmasters.dto.TransactionDTO;
import com.coinmasters.entity.Transaction;
import com.coinmasters.exceptions.NoSuchGroupException;
import com.coinmasters.exceptions.NoSuchTransactionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;


    public GroupTransactionResponse getTransactionOfGroup(Long groupId){
        return transactionRepository.findTransactionsByGroup_GroupId(groupId)
                .map(existingTransaction -> GroupTransactionResponse.builder()
                        .transactions(existingTransaction.stream()
                                .map(transaction -> TransactionDTO.builder()
                                        .transactionId(transaction.getTransactionId())
                                        .name(transaction.getName())
                                        .category(transaction.getCategory())
                                        .creatorName(transaction.getUser().getName())
                                        .date(transaction.getDate())
                                        .build()
                                ).collect(Collectors.toList()))
                        .build())
                .orElseThrow(() -> new NoSuchGroupException(String.format("There is no group with groupID - %s", groupId.toString())));

    }

    public TransactionDTO getTransactionDetails(Long transactionID) {
        Optional<Transaction> transaction = transactionRepository.findTransactionsByTransactionId(transactionID);
        if (transaction.isPresent()){
            Transaction existingTransaction = transaction.get();
            return TransactionDTO.builder()
                    .transactionId(existingTransaction.getTransactionId())
                    .name(existingTransaction.getName())
                    .category(existingTransaction.getCategory())
                    .date(existingTransaction.getDate())
                    .creatorName(existingTransaction.getUser().getName())
                    .build();
        }
        else {
            throw new NoSuchTransactionException(String.format("No transaction with id - %s", transactionID));
        }
    }
}