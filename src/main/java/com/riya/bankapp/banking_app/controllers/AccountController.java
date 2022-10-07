package com.riya.bankapp.banking_app.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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


    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@Valid @RequestBody Account account)
    {
        if(InputValidator.accountCreationValidation(account.getOwnername(),account.getBankName()))
        {
            String accountNumber = this.accountService.createAccount(account.getOwnername(),account.getBankName());
            if(accountNumber.equals(""))
            return  new ResponseEntity<>(constants.CREATE_ACCOUNT_FAILED, HttpStatus.PARTIAL_CONTENT);
            else
            return  new ResponseEntity<>(accountNumber, HttpStatus.OK);
        }
        return  new ResponseEntity<>(constants.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/getAccount/{accountNumber}")
    public ResponseEntity<?> getAccount(@PathVariable("accountNumber") String accountNumber) throws Exception
    {
        Account account = null;
        if(InputValidator.isAccountNumberValid(accountNumber))
        {
            account = this.accountService.getAccountDetails(accountNumber);
            return  ResponseEntity.ok(account);
        }
       
        return  new ResponseEntity<>(constants.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
        
    }
    

    @GetMapping("/fetchBalance/{accountNumber}")
    public ResponseEntity<?> getAccountBalance(@PathVariable("accountNumber") String accountNumber) throws Exception
    {
        Account account = null;
        if(InputValidator.isAccountNumberValid(accountNumber))
        {
            account = this.accountService.getAccountDetails(accountNumber);
            return  new ResponseEntity<>(account.getCurrentBalance(), HttpStatus.OK);
        }
       
        return  new ResponseEntity<>(constants.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
        
    }


}
