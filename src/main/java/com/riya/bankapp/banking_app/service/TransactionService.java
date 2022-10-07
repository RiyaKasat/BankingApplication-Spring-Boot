package com.riya.bankapp.banking_app.service;

import com.riya.bankapp.banking_app.entitites.Transaction;
import com.riya.bankapp.banking_app.payloads.ApiResponse;


public interface TransactionService {
    public ApiResponse makeTransaction(Transaction transactionInput) throws Exception;
  
}
