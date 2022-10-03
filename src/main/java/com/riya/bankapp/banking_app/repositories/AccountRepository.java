package com.riya.bankapp.banking_app.repositories;

import com.riya.bankapp.banking_app.entitites.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{
    public Account findByAccountNumberEquals(String AccountNumber);
    
}
