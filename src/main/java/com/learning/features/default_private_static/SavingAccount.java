package com.learning.features.default_private_static;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file SavingAccount
 */
public class SavingAccount implements Bank {

  private double balance;

  public SavingAccount(double balance) {
    this.balance = balance;
  }

  @Override
  public double getBalance() {
    return balance;
  }

  @Override
  public void deposit(double amount) {
    balance += amount;
  }

  @Override
  public void withdraw(double amount) {
    if(balance >= amount) {
      balance -= amount;
    }
    else {
      System.out.println("Insufficient funds");
    }
  }

  @Override
  public void displayAccountType() {
    System.out.println("Account Type: Savings Account");
  }
}
