package com.riya.bankapp.banking_app.service.serviceImplmentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riya.bankapp.banking_app.repositories.AccountRepository;
import com.riya.bankapp.banking_app.repositories.TransactionRepository;
import com.riya.bankapp.banking_app.service.TransactionService;
import com.riya.bankapp.banking_app.entitites.Account;
import com.riya.bankapp.banking_app.entitites.Transaction;
import com.riya.bankapp.banking_app.exceptions.InvalidInputException;
import com.riya.bankapp.banking_app.exceptions.TransactionNotAllowedException;
import com.riya.bankapp.banking_app.payloads.ApiResponse;
import com.riya.bankapp.banking_app.constants.Action;
import java.time.LocalDate;
import java.util.List;
import com.riya.bankapp.banking_app.constants.constants;

@Service
public class TransactionServiceImpl implements TransactionService
{
    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private AccountRepository accountRepo;
 
    Account sourceAccount, targetAccount;
    @Override
    public ApiResponse makeTransaction(Transaction transactionInput) throws Exception {

        String targetAccoutNumber="";
        
        String sourceAccountNumber = transactionInput.getSourceAccountNumber();
        
        if(transactionInput.getTargetAccountNumber() != null)
        targetAccoutNumber = transactionInput.getTargetAccountNumber();

        if(!sourceAccountNumber.equals(""))
        sourceAccount = this.accountRepo.findByAccountNumberEquals(sourceAccountNumber);

        if(!targetAccoutNumber.equals(""))
        targetAccount = this.accountRepo.findByAccountNumberEquals(targetAccoutNumber);

        String transactionType = transactionInput.getTransactionType();
       
        Transaction transactionDetails = new Transaction();
        transactionDetails.setAmount(transactionInput.getAmount());
        transactionDetails.setSourceAccountNumber(sourceAccountNumber);
        transactionDetails.setTargetAccountNumber(targetAccoutNumber);
        transactionDetails.setTransactionDate(LocalDate.now());
        ApiResponse response ;
        switch(transactionType)
        {
            
            case "TRANSFER" :
            if (sourceAccount != null && targetAccount != null) 
            {
                if(checkIfAccAmountIsValid(transactionInput.getAmount(), sourceAccount.getCurrentBalance(), Action.WITHDRAW).isSuccess() &&  isTransactionAllowed(sourceAccount, Action.WITHDRAW).isSuccess()
                && checkIfAccAmountIsValid(transactionInput.getAmount(), targetAccount.getCurrentBalance(), Action.DEPOSIT).isSuccess()  && isTransactionAllowed(targetAccount, Action.DEPOSIT).isSuccess()
                && isAmountWithinLimits(transactionInput.getAmount(), Action.WITHDRAW).isSuccess() && isAmountWithinLimits(transactionInput.getAmount(), Action.DEPOSIT).isSuccess())
                {
                    transactionDetails.setTransactionType("TRANSFER");
                    updateAccountBalance(sourceAccount, transactionDetails.getAmount(), targetAccount, Action.TRANSFER );
                    this.transactionRepo.save(transactionDetails);
                    response = new ApiResponse();
                    response.setMesssage("SuccessFully transaction done for"+ transactionType);
                    response.setSuccess(true);
                    return response;
                }
            }
            break;

            case "WITHDRAW" :
            if (sourceAccount != null)
            {
               
                if(checkIfAccAmountIsValid(transactionInput.getAmount(), sourceAccount.getCurrentBalance(), Action.WITHDRAW).isSuccess() &&  isTransactionAllowed(sourceAccount, Action.WITHDRAW).isSuccess()
                && isAmountWithinLimits(transactionInput.getAmount(), Action.WITHDRAW).isSuccess())
                {
                  
                    transactionDetails.setTransactionType("WITHDRAW");
                    updateAccountBalance(sourceAccount, transactionDetails.getAmount(), targetAccount, Action.WITHDRAW );
                    this.transactionRepo.save(transactionDetails);
                    response = new ApiResponse();
                    response.setMesssage("SuccessFully transaction done for"+ transactionType);
                    response.setSuccess(true);
                    return response;
                }
            }
            break;

            case"DEPOSIT" :
            if(sourceAccount != null)
            {
               
                if(checkIfAccAmountIsValid(transactionInput.getAmount(), sourceAccount.getCurrentBalance(), Action.DEPOSIT).isSuccess()  && isTransactionAllowed(sourceAccount, Action.DEPOSIT).isSuccess()
                && isAmountWithinLimits(transactionInput.getAmount(), Action.DEPOSIT).isSuccess())
                {
                   
                    transactionDetails.setTransactionType("DEPOSIT");
                    updateAccountBalance(sourceAccount, transactionDetails.getAmount(), targetAccount, Action.DEPOSIT );
                    this.transactionRepo.save(transactionDetails);
                    response = new ApiResponse();
                    response.setMesssage("SuccessFully transaction done for"+ transactionType);
                    response.setSuccess(true);
                    return response;
                }
            }
            break;

            default: throw new InvalidInputException("Transaction Unsuccessful","",1);        
        
        
        }
        return new ApiResponse("Unsuccessful", false); 
    }

    

    //If transaction amount is valid
    public ApiResponse isAmountWithinLimits(double transaction_amount, Action action) throws Exception
    {
        ApiResponse response = new ApiResponse();;
        if(((action.equals(Action.WITHDRAW)) && (transaction_amount >= constants.MINIMUM_WITHDRAWAL_LIMIT  && transaction_amount <= constants.MAXIMUM_WITHDRAWAL_LIMIT))
        || ((!(action.equals(Action.WITHDRAW)))&& (transaction_amount >= constants.MINIMUM_DEPOSIT_LIMIT && transaction_amount <= constants.MAXIMUM_DEPOSIT_LIMIT)))
         {
            response.setMesssage("Amount is in limit");
            response.setSuccess(true);
            return response;
         }
    
        else
          throw new InvalidInputException("TransactionAmount Is not withing range  1.Wihdrawal -(1000-25000) 2. Deposit (500-50000)", "a",transaction_amount);
      
    }

    
    
    //checks if account balance is in limit
    public ApiResponse checkIfAccAmountIsValid(double transaction_amount, double accountBalance, Action action) throws Exception
    {
       ApiResponse response = new ApiResponse();
       if(((action == Action.WITHDRAW) && (accountBalance - transaction_amount > constants.MINIMUM_ACCOUNT_BALANCE)) |
          ((action != Action.WITHDRAW) && (accountBalance + transaction_amount <= constants.MAXIMUM_ACCOUNT_BALANCE)))
          {
            
            response.setMesssage("Amount is valid");
            response.setSuccess(true);
            return response;
         }
    
        else
         {
            if(action== Action.WITHDRAW)
            throw new InvalidInputException("Insufficient Account Balance in source Account", "currentBalance",constants.MINIMUM_ACCOUNT_BALANCE);

            else
            throw new InvalidInputException("Maximum Account Balance in target account cannot exceed", "currentBalance",constants.MAXIMUM_ACCOUNT_BALANCE);
         }
         
    }


    //For updating balance
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
       sourceAccount.setCurrentBalance(sourceAccount.getCurrentBalance() + transactionAmount);
    }



    //To check if transaction is allowed or has reached the limit
    public ApiResponse isTransactionAllowed(Account account, Action action) throws Exception
    {   

        return fetchedTotalTransactionsSoFarInDay(account, action);
    }



    public ApiResponse fetchedTotalTransactionsSoFarInDay(Account account,  Action action) throws Exception
    {
        List<Transaction> list = getTransactionList(account, action);
        
        int[] transactionDetails = getTransctionDetails(list);
        ApiResponse response = new ApiResponse();
        if((action.equals(Action.WITHDRAW) && transactionDetails[0]<= constants.MINIMUM_TRANSACTION_ALLOWED) || (action != Action.WITHDRAW && (transactionDetails[1]<= constants.MINIMUM_TRANSACTION_ALLOWED)))
        {
                System.out.println("Inside fetched");
                response.setMesssage("Particular Transaction Allowed - TransactionType"+action);
                response.setSuccess(true);
                return response;
        }
        else
        throw new TransactionNotAllowedException("Not more than  3 transactions allowed for the operation",action);
    }



    //Helper method to check transactions done so far in a day for particular account  
    public List<Transaction> getTransactionList(Account account, Action action)
    {
       List<Transaction> list;
       if(action== Action.WITHDRAW)
        list = this.transactionRepo.findBySourceAccountNumberAndTransactionDate(account.getAccountNumber(), LocalDate.now());

       else
       list = this.transactionRepo.findByTargetAccountNumberAndTransactionDate(account.getAccountNumber(), LocalDate.now());

       return list;
    }




    public int[] getTransctionDetails(List<Transaction> transactions)
    {
        int withdrawalCount = 0;
        int depositCount = 0;
         
        for(int i=0; i < transactions.size(); i++)
        {
           if(transactions.get(i).getTransactionType().equals("WITHDRAW"))
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
