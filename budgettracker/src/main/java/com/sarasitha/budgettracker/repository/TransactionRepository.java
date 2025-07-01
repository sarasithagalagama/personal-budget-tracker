package com.sarasitha.budgettracker.repository;

import com.sarasitha.budgettracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
