package com.riya.bankapp.banking_app.utils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.riya.bankapp.banking_app.entitites.Account;

public class DepositInput {
    
    @NotBlank(message = "Target account number should not be blank")
    private Account targetAccountNumber;

    @Positive(message="Amount to deposit should be positive")
    @Min(value=500, message="Min amount to deposit should be 500")
    private double amount;
}
