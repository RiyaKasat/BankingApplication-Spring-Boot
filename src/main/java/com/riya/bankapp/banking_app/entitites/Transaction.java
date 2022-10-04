package com.riya.bankapp.banking_app.entitites;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Positive;
import javax.persistence.*;

import lombok.*;

import java.time.LocalDate;



@Getter
@Setter
@Entity
@Table(name="transaction", schema="banking-app")



@SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_sequence", schema = "banking-app", initialValue = 5)
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="transaction_seq")
    private Long transactionId;

    @Column(unique = true)
    private String sourceAccountNumber;

    @Column(unique = true)
    private String targetAccountNumber;
    
    @Positive(message = "Amount should be positive")
    private double amount;
    private LocalDate transactionDate;

    private String transactionType;
  
}
