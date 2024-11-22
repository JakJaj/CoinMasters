package com.coinmasters.dao;

import com.coinmasters.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<List<Transaction>> findTransactionsByGroup_GroupId(Long groupId);

}
