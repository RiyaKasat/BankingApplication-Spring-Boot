package com.riya.bankapp.banking_app.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riya.bankapp.banking_app.entitites.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountNumberAndTransactionDate(String accountNumber, LocalDate date);
}
