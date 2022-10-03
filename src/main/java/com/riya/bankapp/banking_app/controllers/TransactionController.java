package com.riya.bankapp.banking_app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riya.bankapp.banking_app.entitites.Transaction;
import com.riya.bankapp.banking_app.service.TransactionService;
import com.riya.bankapp.banking_app.utils.InputValidator;
import com.riya.bankapp.banking_app.constants.constants;


@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    
  
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/initiateTransaction")
    public ResponseEntity<?> makeTransfer(@Valid @RequestBody Transaction transaction )
    {
        if(InputValidator.checkIfValidTransaction(transaction))
        {
           boolean isSuccessful = this.transactionService.makeTransaction(transaction);
           return new ResponseEntity<>(isSuccessful, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(constants.INVALID_TRANSACTION, HttpStatus.BAD_REQUEST);
        }
    }



    
}

