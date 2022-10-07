package com.riya.bankapp.banking_app.exceptions;
import lombok.*;

@Getter
@Setter
public class AccountNotFoundException extends RuntimeException {
    String message;
    String accountNumber;

    public AccountNotFoundException(String message, String accountNumber)
    {
        super();
        this.message = message;
        this.accountNumber = accountNumber;
    }

}
