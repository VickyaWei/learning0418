package com.learning.features.default_private_static;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file Bank
 */
public interface Bank {
  double getBalance();
  void deposit(double amount);
  void withdraw(double amount);

  // Default method
  default void displayAccountType() {
    System.out.println("Account Type: Generic");
  }

  default void executeTransaction(String type, double amount) {
    if(type.equals("Deposit")) {
      deposit(amount);
      logTransaction(type, amount);
    }
    else if(type.equals("Withdraw")) {
      withdraw(amount);
      logTransaction(type, amount);
    }
    else{
      System.out.println("Invalid transaction type");
    }
  }

  // private method
  // used in executeTransaction() method above
  private void logTransaction(String transactionType, double amount) {
    System.out.println("Transaction: " + transactionType);
    System.out.println("Amount: $" + amount);
    System.out.println("Current Balance: $" + getBalance());
  }

  // static method
  static Bank createAccount(double initialBalance) {
    return new SavingAccount(initialBalance);
  }
}
