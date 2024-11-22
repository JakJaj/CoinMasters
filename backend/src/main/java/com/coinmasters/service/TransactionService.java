package com.coinmasters.service;


import com.coinmasters.config.JwtService;
import com.coinmasters.controller.transactions.GroupTransactionResponse;
import com.coinmasters.controller.transactions.TransactionAddRequest;
import com.coinmasters.dao.GroupRepository;
import com.coinmasters.dao.TransactionRepository;
import com.coinmasters.dao.UserRepository;
import com.coinmasters.dto.TransactionDTO;
import com.coinmasters.entity.Group;
import com.coinmasters.entity.Transaction;
import com.coinmasters.entity.User;
import com.coinmasters.exceptions.NoSuchGroupException;
import com.coinmasters.exceptions.NoSuchTransactionException;
import com.coinmasters.exceptions.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public GroupTransactionResponse getTransactionOfGroup(Long groupId){
        return transactionRepository.findTransactionsByGroup_GroupId(groupId)
                .map(existingTransaction -> GroupTransactionResponse.builder()
                        .transactions(existingTransaction.stream()
                                .map(transaction -> TransactionDTO.builder()
                                        .transactionId(transaction.getTransactionId())
                                        .name(transaction.getName())
                                        .category(transaction.getCategory())
                                        .creatorName(transaction.getUser().getName())
                                        .amount(transaction.getAmount())
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
                    .amount(existingTransaction.getAmount())
                    .build();
        }
        else {
            throw new NoSuchTransactionException(String.format("No transaction with id - %s", transactionID));
        }
    }

    public TransactionDTO addTransaction(TransactionAddRequest request, String token) {


        String email = jwtService.extractUsername(token.substring(7));
        Optional<Group> group = groupRepository.getGroupByGroupId(request.getGroupId());
        Optional<User> user = userRepository.findByEmail(email);

        if (group.isPresent() && user.isPresent()) {
            Transaction transactionToSave = Transaction.builder()
                    .name(request.getName())
                    .category(request.getCategory())
                    .date(request.getDate())
                    .amount(request.getAmount())
                    .group(group.get())
                    .user(user.get())
                    .build();
            Transaction savedTransaction = transactionRepository.save(transactionToSave);
            return TransactionDTO.builder()
                    .transactionId(savedTransaction.getTransactionId())
                    .name(savedTransaction.getName())
                    .category(savedTransaction.getCategory())
                    .date(savedTransaction.getDate())
                    .creatorName(savedTransaction.getUser().getName())
                    .amount(savedTransaction.getAmount())
                    .build();

        } else if (group.isEmpty()) {
            throw new NoSuchGroupException(String.format("No group with id - %s", request.getGroupId()));
        }else {
            throw new NoSuchUserException("This should never happen. If it did then, like, damn...");
        }

    }
}