package com.riya.bankapp.banking_app.constants;

import java.util.regex.Pattern;

public class constants {

    public static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[0-9]{8}$");
    public static final String ACCOUNT_NUMBER_PATTERN_STRING = "[0-9]{8}";
    public static final String INVALID_TRANSACTION =
    "Account information is invalid or transaction has been denied";
    
    public static final String INVALID_SEARCH_CRITERIA =
            "The provided account number did not match the expected format";
    public static final String CREATE_ACCOUNT_FAILED =
            "Error happened during creating new account";

    public static final String SUCCESS =
             "Operation completed successfully";

    public static final Double MINIMUM_WITHDRAWAL_LIMIT = 1000.00;
    public static final Double MAXIMUM_WITHDRAWAL_LIMIT = 25000.00;

    public static final Double MINIMUM_DEPOSIT_LIMIT = 500.00;
    public static final Double MAXIMUM_DEPOSIT_LIMIT = 50000.00;

    public static final Double MINIMUM_ACCOUNT_BALANCE = 0.00;
    public static final Double MAXIMUM_ACCOUNT_BALANCE = 100000.00;
    
    public static final int    MINIMUM_TRANSACTION_ALLOWED = 3;
    public static final String TARGET_ACCOUNT_REQUIRED = "Target Account Number required";
    public static final String SOURCE_ACCOUNT_REQUIRED = "Source Account Number required";
}

