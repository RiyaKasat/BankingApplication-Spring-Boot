package com.riya.bankapp.banking_app.exceptions;

import com.riya.bankapp.banking_app.constants.Action;

import lombok.*;

@Getter
@Setter
public class TransactionNotAllowedException extends RuntimeException {
    String resourceName;
    
    Action fieldValue;

    public TransactionNotAllowedException(String resourceName,  Action fieldValue)
    {
        super(String.format("%s : %s", resourceName,  fieldValue));
        this.resourceName = resourceName;
   
        this.fieldValue = fieldValue;
    }
}
