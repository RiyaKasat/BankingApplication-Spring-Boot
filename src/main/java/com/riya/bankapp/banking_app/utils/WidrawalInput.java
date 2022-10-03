package com.riya.bankapp.banking_app.utils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.riya.bankapp.banking_app.entitites.Account;

public class WidrawalInput {
    
    @NotBlank(message="Account Number from where to withdrwa money should not be blank")
    private Account withdrawalAccountNumber;
    

    @Positive(message="Amount should be positive")
    @Min(value=1000, message="Minimum withdrwal amount should be 1000")
    private double amount;
}
