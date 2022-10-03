package com.riya.bankapp.banking_app.utils;

import com.mifmif.common.regex.Generex;
import static com.riya.bankapp.banking_app.constants.constants.ACCOUNT_NUMBER_PATTERN_STRING;
public class AccountNumberGenerator {

    
    Generex accountNumberGenerator = new Generex(ACCOUNT_NUMBER_PATTERN_STRING);
  
    public AccountNumberGenerator(){}
    
    public String generateAccountNumber() {
        return accountNumberGenerator.random();
    }
}
