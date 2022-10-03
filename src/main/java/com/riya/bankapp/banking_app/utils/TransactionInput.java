package com.riya.bankapp.banking_app.utils;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import com.riya.bankapp.banking_app.entitites.Account;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionInput {
    
    private Account fromAccountNumber;
    private Account toAccountNumber;

    @Positive(message="Transferable Amount should be positive")
    @Min(value = 1000, message ="Min amount should be 1000")
    private double amount;
    
}
