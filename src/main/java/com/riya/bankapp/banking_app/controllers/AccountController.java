package com.riya.bankapp.banking_app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riya.bankapp.banking_app.constants.constants;
import com.riya.bankapp.banking_app.entitites.Account;
import com.riya.bankapp.banking_app.service.AccountService;
import com.riya.bankapp.banking_app.utils.InputValidator;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    
    @Autowired
    private AccountService accountService;


    @PostMapping("/create/{bankName}/{ownerName}")
    public ResponseEntity<?> createAccount(@PathVariable("ownerName") String ownerName, @PathVariable("bankName") String bankName)
    {
        if(InputValidator.accountCreationValidation(ownerName,bankName))
        {
            String accountNumber = this.accountService.createAccount(ownerName, bankName);
            if(accountNumber.equals(""))
            return  new ResponseEntity<>(constants.CREATE_ACCOUNT_FAILED, HttpStatus.PARTIAL_CONTENT);
            else
            return  new ResponseEntity<>(constants.SUCCESS, HttpStatus.OK);
        }
        return  new ResponseEntity<>(constants.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable("accountNumber") String accountNumber)
    {
        Account account = null;
        if(InputValidator.isAccountNumberValid(accountNumber))
        {
            account = this.accountService.getAccountDetails(accountNumber);
            return  ResponseEntity.ok(account);
        }
       
        return ResponseEntity.ok(account);
        
    }
    
}
