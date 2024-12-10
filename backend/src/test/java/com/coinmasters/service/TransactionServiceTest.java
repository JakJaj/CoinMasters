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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TransactionService transactionService;

    private User user;
    private Group group;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");

        group = new Group();
        group.setGroupId(1L);

        transaction = new Transaction();
        transaction.setTransactionId(1L);
        transaction.setName("Test Transaction");
        transaction.setCategory("Test Category");
        transaction.setUser(user);
        transaction.setGroup(group);
    }

    @Test
    void testGetTransactionOfGroup() {
        when(transactionRepository.findTransactionsByGroup_GroupId(1L)).thenReturn(Optional.of(Collections.singletonList(transaction)));

        GroupTransactionResponse response = transactionService.getTransactionOfGroup(1L);

        assertNotNull(response);
        assertEquals(1, response.getTransactions().size());
        assertEquals("Test Transaction", response.getTransactions().get(0).getName());
    }

    @Test
    void testGetTransactionDetails() {
        when(transactionRepository.findTransactionsByTransactionId(1L)).thenReturn(Optional.of(transaction));

        TransactionDTO transactionDTO = transactionService.getTransactionDetails(1L);

        assertNotNull(transactionDTO);
        assertEquals("Test Transaction", transactionDTO.getName());
    }

    @Test
    void testAddTransaction() {
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");
        when(groupRepository.getGroupByGroupId(1L)).thenReturn(Optional.of(group));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionAddRequest request = new TransactionAddRequest();
        request.setName("Test Transaction");
        request.setCategory("Test Category");
        request.setDate("2023-10-10");
        request.setAmount(100.0f);
        request.setGroupId(1L);

        TransactionDTO transactionDTO = transactionService.addTransaction(request, "Bearer token");

        assertNotNull(transactionDTO);
        assertEquals("Test Transaction", transactionDTO.getName());
    }

    @Test
    void testDeleteTransaction() {
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(transactionRepository.findTransactionsByTransactionId(1L)).thenReturn(Optional.of(transaction));

        UserGroup userGroup = new UserGroup();
        userGroup.setGroup(group);
        user.setUserGroups(Set.of(userGroup));

        DeleteTransactionResponse response = transactionService.deleteTransaction(1L, "Bearer token");

        assertNotNull(response);
        assertEquals("Deleted", response.getStatus());
    }

    @Test
    void testChangeTransactionDetails() {
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(transactionRepository.findTransactionsByTransactionId(1L)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        ChangeTransactionDetailsRequest request = new ChangeTransactionDetailsRequest();
        request.setNewName("Updated Transaction");
        request.setNewCategory("Updated Category");
        request.setNewDate("2023-10-11");
        request.setNewAmount(200.0f);

        ChangeTransactionDetailsResponse response = transactionService.changeTransactionDetails(1L, request, "Bearer token");

        assertNotNull(response);
        assertEquals("Success", response.getStatus());
        assertEquals("Updated Transaction", response.getTransaction().getName());
    }
}