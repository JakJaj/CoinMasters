package com.coinmasters.service;


import com.coinmasters.controller.transactions.GroupTransactionResponse;
import com.coinmasters.dao.TransactionRepository;
import com.coinmasters.dto.TransactionDTO;
import com.coinmasters.exceptions.NoSuchGroupException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}