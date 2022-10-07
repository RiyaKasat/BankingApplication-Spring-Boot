package com.riya.bankapp.banking_app.service;

import com.riya.bankapp.banking_app.entitites.Account;


public interface AccountService {
    public String createAccount(String ownername, String bankName);
    public Account getAccountDetails(String accountNumber) throws Exception;
    public double getCurrentAccountBalance(String accountNumber) throws Exception;
}
