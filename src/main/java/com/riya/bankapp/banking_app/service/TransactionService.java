package com.riya.bankapp.banking_app.service;

import com.riya.bankapp.banking_app.entitites.Transaction;


public interface TransactionService {
    public boolean makeTransaction(Transaction transactionInput);

}
