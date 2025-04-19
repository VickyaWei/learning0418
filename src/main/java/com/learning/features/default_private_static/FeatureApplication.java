package com.learning.features.default_private_static;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file FeatureApplication
 */

@SpringBootApplication
public class FeatureApplication {

  public static void main(String[] args) {
    // call static method without creating an object
    // Bank account = new SavingAccount(1000);
    Bank account = Bank.createAccount(2000);
    account.displayAccountType();

    account.deposit(100);
    account.withdraw(50);
    account.executeTransaction("Withdraw", 50);
    account.executeTransaction("Deposit", 100);
    System.out.println("Balance: $" + account.getBalance());
  }
}
