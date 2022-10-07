package com.riya.bankapp.banking_app.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.riya.bankapp.banking_app.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler{

   @ExceptionHandler(InvalidInputException.class)
   public ResponseEntity<ApiResponse> invalidInputException(InvalidInputException ex)
   {
      String message = ex.getMessage();
      ApiResponse response = new ApiResponse(message, false);
      return new ResponseEntity<ApiResponse> (response, HttpStatus.BAD_REQUEST);
   }


   @ExceptionHandler(TransactionNotAllowedException.class)
   public ResponseEntity<ApiResponse> transactionNotAllowedException(TransactionNotAllowedException ex)
   {
      String message = ex.getMessage();
      ApiResponse response = new ApiResponse(message, false);
      return new ResponseEntity<ApiResponse> (response, HttpStatus.BAD_REQUEST);
   }


   @ExceptionHandler(AccountNotFoundException.class)
   public ResponseEntity<ApiResponse> accountNumberNotValid(AccountNotFoundException ex)
   {
      String message = ex.getMessage();
      String account =ex.getAccountNumber();
      ApiResponse response = new ApiResponse(message+" with Account Number "+account, false);
      return new ResponseEntity<ApiResponse> (response, HttpStatus.BAD_REQUEST);
   }
}
