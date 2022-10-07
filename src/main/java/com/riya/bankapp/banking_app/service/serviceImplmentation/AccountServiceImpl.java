package com.riya.bankapp.banking_app.service.serviceImplmentation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riya.bankapp.banking_app.entitites.Account;
import com.riya.bankapp.banking_app.exceptions.AccountNotFoundException;
import com.riya.bankapp.banking_app.repositories.AccountRepository;
import com.riya.bankapp.banking_app.service.AccountService;
import com.riya.bankapp.banking_app.utils.AccountNumberGenerator;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepo;

    
    @Override
    public String createAccount(String ownername, String bankName) {
        AccountNumberGenerator accNumGen = new AccountNumberGenerator();
        Account account = new Account(accNumGen.generateAccountNumber(), ownername , 0.0, bankName);
        this.accountRepo.save(account);
        return account.getAccountNumber();
    }

    @Override
    public Account getAccountDetails(String accountNumber) throws Exception{
        Account account = this.accountRepo.findByAccountNumberEquals(accountNumber);
        if(account != null)
        return account;   
        else
        throw new AccountNotFoundException("Provided Account Not Found", accountNumber);
    }

    @Override
    public double getCurrentAccountBalance(String accountNumber) throws Exception {
       Account account = getAccountDetails(accountNumber);
       return account.getCurrentBalance();
    }
    


}
