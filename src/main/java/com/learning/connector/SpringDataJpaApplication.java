package com.learning.connector;


import com.learning.connector.exception.AccountNotFoundException;
import com.learning.connector.model.Account;
import com.learning.connector.model.TransactionHistory;
import com.learning.connector.service.AccountService;
import com.learning.connector.service.TransactionHistoryService;
import com.learning.connector.service.TransactionService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
public class SpringDataJpaApplication {

  public static void main(String[] args) {

    // PostgresSQL
    ConfigurableApplicationContext context = SpringApplication.run(SpringDataJpaApplication.class, args);
    TransactionService transactionService = context.getBean(TransactionService.class);
    transactionService.transferMoney("ACC123", "ACC456", new BigDecimal("100.00"));



    // account not found exception
    AccountService accountService = context.getBean(AccountService.class);
    try {
      Account firstCall = accountService.getAccountByNumber("ACC123");
      System.out.println("Account found: " + firstCall.getAccountNumber() + ", Balance: " + firstCall.getBalance());
    } catch (AccountNotFoundException ex) {
      System.out.println("Account not found: " + ex.getMessage());
    }




    // Cassandra
    TransactionHistoryService transactionHistoryService = context.getBean(TransactionHistoryService.class);

    System.out.println("\nTesting Cassandra - Retrieving transaction history for ACC123:");
    List<TransactionHistory> sourceAccountHistory = transactionHistoryService.getTransactionHistory("ACC123");
    System.out.println("Retrieved " + sourceAccountHistory.size() + " transactions for ACC123");
    sourceAccountHistory.forEach(tx -> {
      System.out.println("Transaction ID: " + tx.getTransactionId());
      System.out.println("Date: " + tx.getTransactionDate());
      System.out.println("Type: " + tx.getTransactionType());
      System.out.println("Amount: " + tx.getAmount());
      System.out.println("Related Account: " + tx.getRelatedAccount());
      System.out.println("Description: " + tx.getDescription());
      System.out.println("------------------");
    });
  }
}
