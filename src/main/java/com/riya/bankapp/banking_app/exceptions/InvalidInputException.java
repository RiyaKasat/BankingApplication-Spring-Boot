package com.riya.bankapp.banking_app.exceptions;

import lombok.*;

@Getter
@Setter
public class InvalidInputException extends RuntimeException {
    String resourceName;
    String fieldName;
    double fieldValue;

    public InvalidInputException(String resourceName, String fieldName, double fieldValue)
    {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
