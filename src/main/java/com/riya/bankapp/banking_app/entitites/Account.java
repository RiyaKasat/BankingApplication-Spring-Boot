package com.riya.bankapp.banking_app.entitites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Getter
@Setter
@Entity
@Table(name="account")

public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long account_id;

    @Column(unique = true)
    private String account_no;

    private double currentBalance;
    private String ownername;

    private String bankName;
    
    public Account(String accountNumber, String ownerName, double d, String bankName)
    {
        this.account_no = accountNumber;
        this.ownername= ownerName;
        this.currentBalance = d;
        this.bankName = bankName;
    }
    public Account orElseThrow(Object object) {
        return null;
    }
}
