package com.riya.bankapp.banking_app.utils;


import com.riya.bankapp.banking_app.constants.constants;
import com.riya.bankapp.banking_app.entitites.Transaction;


public class InputValidator {

    // @Autowired
    // private AccountService accountService;
    
    public static boolean accountCreationValidation(String ownerName, String BankName)
    {
      if(ownerName.equals("")||BankName.equals(""))
      return false;
      return true;
    }

    public static boolean isAccountNumberValid(String accountNumber)
    {
        return constants.ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).find();
    }


    public static boolean checkIfValidTransaction(Transaction transaction, String transactionType) {

        if(!transactionType.equals("TRANSFER" ))
        {
            if (!isAccountNumberValid(transaction.getSourceAccountNumber()))
            return false;
        }
        
        
        else
        {
        if (!isAccountNumberValid(transaction.getTargetAccountNumber()) && !isAccountNumberValid(transaction.getSourceAccountNumber()))
            return false;

        if (transaction.getSourceAccountNumber().equals(transaction.getTargetAccountNumber()))
            return false;

        }
        return true;
    }
}
