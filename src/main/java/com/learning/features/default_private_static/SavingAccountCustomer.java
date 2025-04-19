package com.learning.features.default_private_static;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file SaveingAccountCustomer
 */
public class SavingAccountCustomer implements Customer {

  private double balance;

  public SavingAccountCustomer() {
    balance = 0;
  }

  @Override
  public void openAccount() {
    System.out.println("Opening a saving account");
  }

  @Override
  public void closeAccount() {
    System.out.println("Closing a saving account");
  }

  @Override
  public void deposit(double amount) {
    balance += amount;
    System.out.println("Deposited $" + amount + ". New balance: $" + balance);
  }

  @Override
  public void withdraw(double amount) {
    if(balance >= amount) {
      balance -= amount;
      System.out.println("Withdrawn $" + amount + ". New balance: $" + balance);
    } else {
      System.out.println("Insufficient funds");
    }
  }
}
