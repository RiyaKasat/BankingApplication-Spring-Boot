package com.riya.bankapp.banking_app.service.serviceImplmentation;

import org.springframework.beans.factory.annotation.Autowired;

import com.riya.bankapp.banking_app.entitites.Account;
import com.riya.bankapp.banking_app.repositories.AccountRepository;
import com.riya.bankapp.banking_app.service.AccountService;
import com.riya.bankapp.banking_app.utils.AccountNumberGenerator;

public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepo;

    
    @Override
    public String createAccount(String ownername, String bankName) {
        AccountNumberGenerator accNumGen = new AccountNumberGenerator();
        Account account = new Account(accNumGen.generateAccountNumber(), ownername , 0.0, bankName);
        this.accountRepo.save(account);
        return account.getAccount_no();
    }

    @Override
    public Account getAccountDetails(String accountNumber) {
        Account account = this.accountRepo.findByAccountNumberEquals(accountNumber);
        return account;
    }
    
    


}
