package com.learning.connector;


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

@SpringBootApplication
@EnableCaching
public class SpringDataJpaApplication {

  public static void main(String[] args) {

    // PostgresSQL
    ConfigurableApplicationContext context = SpringApplication.run(SpringDataJpaApplication.class, args);
    TransactionService transactionService = context.getBean(TransactionService.class);
    transactionService.transferMoney("ACC123", "ACC456", new BigDecimal("100.00"));

    // Redis
    System.out.println("First call to getAccountByNumber:");
    AccountService accountService = context.getBean(AccountService.class);
    Optional<Account> firstCall = accountService.getAccountByNumber("ACC123");
    firstCall.ifPresent(account -> System.out.println("Account found: " + account.getAccountNumber() + ", Balance: " + account.getBalance()));

    System.out.println("\nSecond call to getAccountByNumber:");
    Optional<Account> secondCall = accountService.getAccountByNumber("ACC123");
    secondCall.ifPresent(account -> System.out.println("Account found: " + account.getAccountNumber() + ", Balance: " + account.getBalance()));

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
