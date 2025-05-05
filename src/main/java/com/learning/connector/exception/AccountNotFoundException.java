package com.learning.connector.exception;

public class AccountNotFoundException extends RuntimeException {
  public AccountNotFoundException(String accountNumber) {
    super("Account with number " + accountNumber + " does not exist.");
  }
}
