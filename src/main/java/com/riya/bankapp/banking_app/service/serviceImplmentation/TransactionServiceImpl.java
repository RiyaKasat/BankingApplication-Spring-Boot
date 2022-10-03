package com.riya.bankapp.banking_app.service.serviceImplmentation;

import org.springframework.beans.factory.annotation.Autowired;
import com.riya.bankapp.banking_app.repositories.AccountRepository;
import com.riya.bankapp.banking_app.repositories.TransactionRepository;
import com.riya.bankapp.banking_app.service.TransactionService;
import com.riya.bankapp.banking_app.entitites.Account;
import com.riya.bankapp.banking_app.entitites.Transaction;
import com.riya.bankapp.banking_app.constants.Action;
import java.time.LocalDate;
import java.util.List;



public class TransactionServiceImpl implements TransactionService
{
    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private AccountRepository accountRepo;
 

    @Override
    public boolean makeTransaction(Transaction transactionInput) {


        String sourceAccountNumber = transactionInput.getSourceAccountNumber();
        String targetAccoutNumber = transactionInput.getTargetAccountNumber();

        Account sourceAccount = this.accountRepo.findByAccountNumberEquals(sourceAccountNumber);
        Account targetAccount = this.accountRepo.findByAccountNumberEquals(targetAccoutNumber);
        String transactionType = transactionInput.getTransactionType();
       
        Transaction transactionDetails = new Transaction();
        transactionDetails.setAmount(transactionInput.getAmount());
        transactionDetails.setSourceAccountNumber(sourceAccountNumber);
        transactionDetails.setTargetAccountNumber(targetAccoutNumber);
        transactionDetails.setTransactionDate(LocalDate.now());
        switch(transactionType)
        {
            case "TRANSFER" :
            if (sourceAccount != null && targetAccount != null) 
            {
                if(checkIfAccAmountIsValid(transactionInput.getAmount(), sourceAccount.getCurrentBalance(), Action.WITHDRAW) &&  isTransactionAllowed(sourceAccount, Action.WITHDRAW)
                && checkIfAccAmountIsValid(transactionInput.getAmount(), targetAccount.getCurrentBalance(), Action.DEPOSIT)  && isTransactionAllowed(targetAccount, Action.DEPOSIT) 
                && isAmountWithinLimits(transactionInput.getAmount(), Action.WITHDRAW) && isAmountWithinLimits(transactionInput.getAmount(), Action.DEPOSIT))
                {
                    transactionDetails.setTransactionType("TRANSFER");
                    updateAccountBalance(sourceAccount, transactionDetails.getAmount(), targetAccount, Action.TRANSFER );
                    this.transactionRepo.save(transactionDetails);
                    return true;
                }
            }
            break;

            case "WITHDRAW" :
            if (sourceAccount != null)
            {
                if(checkIfAccAmountIsValid(transactionInput.getAmount(), sourceAccount.getCurrentBalance(), Action.WITHDRAW) &&  isTransactionAllowed(sourceAccount, Action.WITHDRAW) 
                && isAmountWithinLimits(transactionInput.getAmount(), Action.WITHDRAW))
                {
                    transactionDetails.setTransactionType("WITHDRAW");
                    updateAccountBalance(sourceAccount, transactionDetails.getAmount(), targetAccount, Action.WITHDRAW );
                    this.transactionRepo.save(transactionDetails);
                    return true;
                }
            }
            break;

            case"DEPOSIT" :
            if(targetAccount != null)
            {
                if(checkIfAccAmountIsValid(transactionInput.getAmount(), targetAccount.getCurrentBalance(), Action.DEPOSIT)  && isTransactionAllowed(targetAccount, Action.DEPOSIT) 
                && isAmountWithinLimits(transactionInput.getAmount(), Action.DEPOSIT))
                {
                    transactionDetails.setTransactionType("DEPOSIT");
                    updateAccountBalance(sourceAccount, transactionDetails.getAmount(), targetAccount, Action.DEPOSIT );
                    this.transactionRepo.save(transactionDetails);
                    return true;
                }
            }
            break;

            default: return false;        
        
        
        }
        return false;
    }

    
    public boolean isAmountWithinLimits(double transaction_amount, Action action)
    {
        if(action == Action.WITHDRAW)
        return (transaction_amount >= 1000 && transaction_amount <= 25000) ? true: false;

        else
        return (transaction_amount >= 500 && transaction_amount <= 50000) ? true: false;
    }

    
    

    public boolean checkIfAccAmountIsValid(double transaction_amount, double accountBalance, Action action)
    {
       if(action == Action.WITHDRAW)
       return (accountBalance - transaction_amount > 0) ? true : false;

       else
       return (accountBalance + transaction_amount <= 100000) ? true: false;
    }
    
    public void updateAccountBalance(Account sourceAccount, double transactionAmount, Account targetAccount, Action action)
    {
       if(action == Action.TRANSFER)
       {
          sourceAccount.setCurrentBalance((sourceAccount.getCurrentBalance() - transactionAmount));
          targetAccount.setCurrentBalance(targetAccount.getCurrentBalance() + transactionAmount);
       }

       else if(action == Action.WITHDRAW)
       sourceAccount.setCurrentBalance((sourceAccount.getCurrentBalance() - transactionAmount));

       else
       targetAccount.setCurrentBalance(targetAccount.getCurrentBalance() + transactionAmount);
    }


    public boolean isTransactionAllowed(Account account, Action action)
    {
        return fetchedTotalTransactionsSoFarInDay(account, action);
    }



    public boolean fetchedTotalTransactionsSoFarInDay(Account account,  Action action)
    {
        List<Transaction> sourcetransactionsList = this.transactionRepo.findBySourceAccountNumberAndTransactionDate(account.getAccount_no(), LocalDate.now());
        int[] sourceTransactionDetails = getTransctionDetails(sourcetransactionsList);
        if(action == Action.WITHDRAW)
          return (sourceTransactionDetails[0]<3) ? true:false;

        else
        return (sourceTransactionDetails[1]<3) ? true:false;

    }

    public int[] getTransctionDetails(List<Transaction> transactions)
    {
        int withdrawalCount = 0;
        int depositCount = 0;

        for(int i=0; i < transactions.size(); i++)
        {
           if(transactions.get(i).getTransactionType() == "WITHDRAW")
           withdrawalCount++;

           else
           depositCount++;
        }

        int[] transactioncount = new int[2];
        transactioncount[0] = withdrawalCount;
        transactioncount[1] = depositCount;
        return transactioncount;
    }


}
