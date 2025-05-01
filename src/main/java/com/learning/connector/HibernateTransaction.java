package com.learning.connector;

import com.learning.connector.model.Account;
import com.learning.connector.model.TransactionLog;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateTransaction {
  public static void main(String[] args) {
    // Create a session factory
    SessionFactory sessionFactory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Account.class)
        .addAnnotatedClass(TransactionLog.class)
        .buildSessionFactory();

    try {
      // Create tables and sample data if needed
      createTablesAndSampleData(sessionFactory);

      // Demonstrate first-level cache
      demonstrateFirstLevelCache(sessionFactory);

      // Demonstrate second-level cache
      demonstrateSecondLevelCache(sessionFactory);

      // Transfer money
      transferMoney(sessionFactory, "ACC123", "ACC456", new BigDecimal("100.00"));

    } finally {
      sessionFactory.close();
    }
  }


  public static void createTablesAndSampleData(SessionFactory sessionFactory) {
    Session session = sessionFactory.openSession();
    Transaction transaction = null;

    try {
      transaction = session.beginTransaction();

      // Check if sample data already exists
      Account account1 = session.get(Account.class, "ACC123");
      Account account2 = session.get(Account.class, "ACC456");

      // Create sample data if needed
      if (account1 == null) {
        account1 = new Account("ACC123", new BigDecimal("1000.00"));
        session.save(account1);
        System.out.println("Created account ACC123");
      }

      if (account2 == null) {
        account2 = new Account("ACC456", new BigDecimal("500.00"));
        session.save(account2);
        System.out.println("Created account ACC456");
      }

      transaction.commit();
      System.out.println("Tables and sample data initialized successfully");

    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      System.out.println("Error initializing database: " + e.getMessage());
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static void demonstrateFirstLevelCache(SessionFactory sessionFactory) {
    Session session = sessionFactory.openSession();
    try {
      // First query will hit the database
      System.out.println("\n--- First-Level Cache Demonstration ---");
      System.out.println("First query - will hit the database:");
      Account account = session.get(Account.class, "ACC123");
      System.out.println("Retrieved: " + account);

      // Second query in the same session will use first-level cache
      System.out.println("\nSecond query - will use first-level cache:");
      account = session.get(Account.class, "ACC123");
      System.out.println("Retrieved from cache: " + account);

      // Clear the session cache
      System.out.println("\nClearing first-level cache");
      session.clear();

      // This query will hit the database again
      System.out.println("\nQuery after clearing cache - will hit the database again:");
      account = session.get(Account.class, "ACC123");
      System.out.println("Retrieved: " + account);

    } finally {
      session.close();
    }
  }

  public static void demonstrateSecondLevelCache(SessionFactory sessionFactory) {
    System.out.println("\n--- Second-Level Cache Demonstration ---");

    // First session
    System.out.println("First session - will hit the database:");
    Session firstSession = sessionFactory.openSession();
    try {
      Account account = firstSession.get(Account.class, "ACC123");
      System.out.println("Retrieved: " + account);
    } finally {
      firstSession.close();
    }

    // Second session - should use second-level cache
    System.out.println("\nSecond session - should use second-level cache:");
    Session secondSession = sessionFactory.openSession();
    try {
      Account account = secondSession.get(Account.class, "ACC123");
      System.out.println("Retrieved from second-level cache: " + account);
    } finally {
      secondSession.close();
    }

    // Evict specific entity from second-level cache
    System.out.println("\nEvicting ACC123 from second-level cache");
    sessionFactory.getCache().evict(Account.class, "ACC123");

    // Third session - will hit the database again
    System.out.println("\nThird session after eviction - will hit the database:");
    Session thirdSession = sessionFactory.openSession();
    try {
      Account account = thirdSession.get(Account.class, "ACC123");
      System.out.println("Retrieved: " + account);
    } finally {
      thirdSession.close();
    }
  }

  public static void transferMoney(SessionFactory sessionFactory,
      String fromAccountNumber,
      String toAccountNumber,
      BigDecimal amount) {
    Session session = sessionFactory.openSession();
    Transaction transaction = null;

    try {
      // 1. Begin transaction
      transaction = session.beginTransaction();

      // 2. Get accounts
      Account fromAccount = session.get(Account.class, fromAccountNumber);
      Account toAccount = session.get(Account.class, toAccountNumber);

      // 3. Check if accounts exist
      if (fromAccount == null || toAccount == null) {
        throw new Exception("One or both accounts not found");
      }

      // 4. Transfer money
      fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
      toAccount.setBalance(toAccount.getBalance().add(amount));

      // 5. Log the transaction
      TransactionLog log = new TransactionLog();
      log.setFromAccount(fromAccountNumber);
      log.setToAccount(toAccountNumber);
      log.setAmount(amount);
      log.setTransactionDate(LocalDate.from(LocalDateTime.now()));

      // 6. Save the changes
      session.update(fromAccount);
      session.update(toAccount);
      session.save(log);

      // 7. Commit the transaction
      transaction.commit();
      System.out.println("Transaction completed successfully");

    } catch (Exception e) {
      // 8. Rollback on exception
      if (transaction != null) {
        transaction.rollback();
      }
      System.out.println("Transaction failed: " + e.getMessage());
      e.printStackTrace();
    } finally {
      // 9. Close the session
      session.close();
    }
  }

}
