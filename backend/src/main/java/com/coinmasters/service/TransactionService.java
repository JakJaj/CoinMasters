package com.coinmasters.service;


import com.coinmasters.config.JwtService;
import com.coinmasters.controller.transactions.*;
import com.coinmasters.dao.GroupRepository;
import com.coinmasters.dao.TransactionRepository;
import com.coinmasters.dao.UserRepository;
import com.coinmasters.dto.TransactionDTO;
import com.coinmasters.entity.Group;
import com.coinmasters.entity.Transaction;
import com.coinmasters.entity.User;
import com.coinmasters.entity.UserGroup.UserGroup;
import com.coinmasters.exceptions.*;
import jakarta.transaction.Transactional;
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

    @Transactional
    public DeleteTransactionResponse deleteTransaction(Long transactionID, String token) {

        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("This should never happen. If it did then, like, damn..."));

        Transaction transaction = transactionRepository.findTransactionsByTransactionId(transactionID)
                .orElseThrow(() -> new NoSuchTransactionException(String.format("No transaction with id - %s", transactionID)));

        for(UserGroup group: user.getUserGroups()){

            if (group.getGroup().getGroupId().longValue() == transaction.getGroup().getGroupId().longValue()){
                transactionRepository.delete(transaction);
                return DeleteTransactionResponse.builder()
                        .status("Deleted")
                        .message("Transaction deleted")
                        .build();
            }
        }

        throw new TransactionDeletionByNonGroupMemberException("Deletion by an user that isn't a group member");

    }

    @Transactional
    public ChangeTransactionDetailsResponse changeTransactionDetails(Long transactionID, ChangeTransactionDetailsRequest request, String token) {

        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("This should never happen. If it did then, like, damn..."));

        Transaction transaction = transactionRepository.findTransactionsByTransactionId(transactionID)
                .orElseThrow(() -> new NoSuchTransactionException(String.format("No transaction with id - %s", transactionID)));

        if (request.getNewName() == null && request.getNewDate() == null && request.getNewCategory() == null){
            throw new NothingToChangeException("No data passed to change");
        }

        if (request.getNewName() != null && !request.getNewName().equals(transaction.getName())){
            transaction.setName(request.getNewName());
        }

        if (request.getNewCategory() != null && !request.getNewCategory().equals(transaction.getCategory())){
            transaction.setCategory(request.getNewCategory());
        }

        if (request.getNewDate() != null && !request.getNewDate().equals(transaction.getDate())){
            transaction.setDate(request.getNewDate());
        }

        if (request.getNewAmount() != 0.0f && request.getNewAmount() != transaction.getAmount()){
            transaction.setName(request.getNewName());
        }

        Transaction savedTransaction = transactionRepository.save(transaction);

        return ChangeTransactionDetailsResponse.builder()
                .status("Success")
                .message("Transaction details changed")
                .transaction(TransactionDTO.builder()
                        .transactionId(savedTransaction.getTransactionId())
                        .name(savedTransaction.getName())
                        .category(savedTransaction.getCategory())
                        .date(savedTransaction.getDate())
                        .creatorName(user.getName())
                        .amount(savedTransaction.getAmount())
                        .build())
                .build();
    }
}