package com.riya.bankapp.banking_app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riya.bankapp.banking_app.entitites.Account;
import com.riya.bankapp.banking_app.entitites.Transaction;
import com.riya.bankapp.banking_app.exceptions.AccountNotFoundException;
import com.riya.bankapp.banking_app.payloads.ApiResponse;
import com.riya.bankapp.banking_app.service.AccountService;
import com.riya.bankapp.banking_app.service.TransactionService;
import com.riya.bankapp.banking_app.utils.InputValidator;
import com.riya.bankapp.banking_app.constants.constants;


@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    
  
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;


    @PostMapping("/initiateTransaction")
    public ResponseEntity<?> makeTransfer(@Valid @RequestBody Transaction transaction ) throws Exception
    { 
        Account sourceAcc, targetAcc;

        if(transaction.getTransactionType().equals("TRANSFER") && transaction.getTargetAccountNumber()==null)
        throw new AccountNotFoundException("Target Account required", transaction.getTargetAccountNumber());

        if( transaction.getSourceAccountNumber()==null)
        throw new AccountNotFoundException("Source Account required", transaction.getTargetAccountNumber());

        if(transaction.getSourceAccountNumber() !=null)
        { 
            sourceAcc = this.accountService.getAccountDetails(transaction.getSourceAccountNumber());
            if(sourceAcc == null)
            throw new AccountNotFoundException("Source Account enteresd not valid", transaction.getSourceAccountNumber());
        }

        if(transaction.getTargetAccountNumber() !=null)
        { 
            targetAcc = this.accountService.getAccountDetails(transaction.getTargetAccountNumber());
            if(targetAcc == null)
            throw new AccountNotFoundException("Target Account enteresd not valid", transaction.getTargetAccountNumber());
        }

       

        if(InputValidator.checkIfValidTransaction(transaction, transaction.getTransactionType()))
        { 
           ApiResponse response = this.transactionService.makeTransaction(transaction);
           if(!response.isSuccess())
           return new ResponseEntity<>(response.getMesssage(), HttpStatus.BAD_REQUEST);
           else
           return new ResponseEntity<>(constants.SUCCESS, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(constants.INVALID_TRANSACTION, HttpStatus.BAD_REQUEST);
        }
    }



    
}

