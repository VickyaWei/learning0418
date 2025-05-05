package com.learning.connector;

import com.learning.connector.model.Account;
import com.learning.connector.model.CustomerProfile;
import com.learning.connector.model.TransactionLog;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import com.mongodb.client.MongoClients;

public class HibernateTransaction {
  public static void main(String[] args) {
    // Create a session factory for PostgreSQL
    SessionFactory sessionFactory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Account.class)
        .addAnnotatedClass(TransactionLog.class)
        .buildSessionFactory();

    // Create MongoDB template
    MongoTemplate mongoTemplate = new MongoTemplate(
        new SimpleMongoClientDatabaseFactory(
            MongoClients.create("mongodb://localhost:27017"), "customer_db"));

    try {
      // Create tables and sample data
      createTablesAndSampleData(sessionFactory, mongoTemplate);


      // Link the existing accounts to customers in MongoDB
      linkExistingAccountsToCustomers(sessionFactory, mongoTemplate);

      // Now transfer money between existing accounts
      transferMoney(sessionFactory, "ACC201", "ACC101", new BigDecimal("10.00"));

    } finally {
      sessionFactory.close();
    }
  }

  public static void linkExistingAccountsToCustomers(SessionFactory sessionFactory, MongoTemplate mongoTemplate) {
    Transaction transaction = null;

    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();

      // Create two customers in MongoDB if they don't exist
      if (!mongoTemplate.collectionExists("customer_profiles")) {
        System.out.println("Creating customers in MongoDB...");

        // Create first customer for ACC101, ACC102
        String customer1Id = "CUST1";
        CustomerProfile customer1 = new CustomerProfile();
        customer1.setId(customer1Id);
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setAccountNumbers(Arrays.asList("ACC101", "ACC102"));
        mongoTemplate.save(customer1);

        // Create second customer for ACC201, ACC202
        String customer2Id = "CUST2";
        CustomerProfile customer2 = new CustomerProfile();
        customer2.setId(customer2Id);
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        customer2.setEmail("jane.smith@example.com");
        customer2.setAccountNumbers(Arrays.asList("ACC201", "ACC202"));
        mongoTemplate.save(customer2);

        System.out.println("Customers created in MongoDB");
      }

      // Link existing accounts to the customers
      @SuppressWarnings("unchecked")
      List<Account> accounts = session.createNativeQuery(
              "SELECT * FROM accounts WHERE account_number IN ('ACC101', 'ACC102', 'ACC201', 'ACC202')",
              Account.class)
          .getResultList();

      if (!accounts.isEmpty()) {
        for (Account account : accounts) {
          if ("ACC101".equals(account.getAccountNumber()) ||
              "ACC102".equals(account.getAccountNumber())) {
            account.setCustomerProfileId("CUST1");
            System.out.println("Linking account " + account.getAccountNumber() + " to customer CUST1");
          } else if ("ACC201".equals(account.getAccountNumber()) ||
              "ACC202".equals(account.getAccountNumber())) {
            account.setCustomerProfileId("CUST2");
            System.out.println("Linking account " + account.getAccountNumber() + " to customer CUST2");
          }
          session.merge(account);
        }
      } else {
        System.out.println("No existing accounts found to link");
      }

      transaction.commit();
      System.out.println("Account linking completed");

    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      System.err.println("Error linking accounts: " + e.getMessage());
      e.printStackTrace();
    }
  }


  public static void createTablesAndSampleData(SessionFactory sessionFactory, MongoTemplate mongoTemplate) {
    Transaction transaction = null;

    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();

      // Check existing data in PostgreSQL first
      boolean accountsExist = checkExistingAccounts(session);

      // Only create accounts if they don't exist
      if (!accountsExist) {
        // Clear existing data in MongoDB for fresh start
        mongoTemplate.dropCollection("customer_profiles");

        // Create first customer with 2 accounts
        String customer1Id = "CUST1";
        CustomerProfile customer1 = new CustomerProfile();
        customer1.setId(customer1Id);
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setAccountNumbers(Arrays.asList("ACC101", "ACC102"));

        // Save customer 1 to MongoDB
        mongoTemplate.save(customer1);
        System.out.println("Created customer CUST1 in MongoDB");

        // Create account 1 for customer 1
        Account account1 = new Account();
        account1.setId(5); // Using different ID to avoid conflict
        account1.setAccountNumber("ACC101");
        account1.setBalance(new BigDecimal("1000.00"));
        account1.setCustomerProfileId(customer1Id);
        session.persist(account1);
        System.out.println("Created account ACC101 for customer CUST1");

        // Create account 2 for customer 1
        Account account2 = new Account();
        account2.setId(6); // Using different ID to avoid conflict
        account2.setAccountNumber("ACC102");
        account2.setBalance(new BigDecimal("500.00"));
        account2.setCustomerProfileId(customer1Id);
        session.persist(account2);
        System.out.println("Created account ACC102 for customer CUST1");

        // Create second customer with 2 accounts
        String customer2Id = "CUST2";
        CustomerProfile customer2 = new CustomerProfile();
        customer2.setId(customer2Id);
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        customer2.setEmail("jane.smith@example.com");
        customer2.setAccountNumbers(Arrays.asList("ACC201", "ACC202"));

        // Save customer 2 to MongoDB
        mongoTemplate.save(customer2);
        System.out.println("Created customer CUST2 in MongoDB");

        // Create account 1 for customer 2
        Account account3 = new Account();
        account3.setId(7); // Using different ID to avoid conflict
        account3.setAccountNumber("ACC201");
        account3.setBalance(new BigDecimal("2000.00"));
        account3.setCustomerProfileId(customer2Id);
        session.persist(account3);
        System.out.println("Created account ACC201 for customer CUST2");

        // Create account 2 for customer 2
        Account account4 = new Account();
        account4.setId(8); // Using different ID to avoid conflict
        account4.setAccountNumber("ACC202");
        account4.setBalance(new BigDecimal("1500.00"));
        account4.setCustomerProfileId(customer2Id);
        session.persist(account4);
        System.out.println("Created account ACC202 for customer CUST2");

        transaction.commit();
        System.out.println("Tables and sample data initialized successfully");
      } else {
        System.out.println("Accounts already exist in the database. Skipping creation.");
        // Make sure transaction is completed
        transaction.commit();
      }
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      System.err.println("Error initializing database: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static boolean checkExistingAccounts(Session session) {
    try {
      // Check if any accounts exist
      Long count = session.createQuery("SELECT COUNT(a) FROM Account a", Long.class).getSingleResult();
      System.out.println("Found " + count + " existing accounts in database");
      return count > 0;
    } catch (Exception e) {
      System.err.println("Error checking existing accounts: " + e.getMessage());
      return false;
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

      // 2. Get accounts by account number using createNativeQuery for more reliable results
      // This fixes the issue with the previous JPQL query
      @SuppressWarnings("unchecked")
      List<Account> fromAccounts = session.createNativeQuery(
              "SELECT * FROM accounts WHERE account_number = :accountNumber", Account.class)
          .setParameter("accountNumber", fromAccountNumber)
          .getResultList();

      @SuppressWarnings("unchecked")
      List<Account> toAccounts = session.createNativeQuery(
              "SELECT * FROM accounts WHERE account_number = :accountNumber", Account.class)
          .setParameter("accountNumber", toAccountNumber)
          .getResultList();

      // 3. Check if accounts exist
      if (fromAccounts.isEmpty() || toAccounts.isEmpty()) {
        throw new Exception("One or both accounts not found. From account: " +
            (fromAccounts.isEmpty() ? "not found" : "found") + ", To account: " +
            (toAccounts.isEmpty() ? "not found" : "found"));
      }

      Account fromAccount = fromAccounts.get(0);
      Account toAccount = toAccounts.get(0);

      System.out.println("Found accounts:");
      System.out.println("From account: " + fromAccount.getAccountNumber() + ", Balance: " + fromAccount.getBalance());
      System.out.println("To account: " + toAccount.getAccountNumber() + ", Balance: " + toAccount.getBalance());

      // 4. Check for sufficient funds
      if (fromAccount.getBalance().compareTo(amount) < 0) {
        throw new Exception("Insufficient funds in account " + fromAccountNumber +
            " (Balance: " + fromAccount.getBalance() + ", Requested: " + amount + ")");
      }

      // 5. Transfer money
      fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
      toAccount.setBalance(toAccount.getBalance().add(amount));

      // 6. Log the transaction
      TransactionLog log = new TransactionLog();
      log.setFromAccount(fromAccountNumber);
      log.setToAccount(toAccountNumber);
      log.setAmount(amount);
      log.setTransactionDate(LocalDate.from(LocalDateTime.now()));

      // 7. Save the changes
      session.merge(fromAccount);
      session.merge(toAccount);
      session.persist(log);

      // 8. Display transaction info
      String transactionType =
          (fromAccount.getCustomerProfileId() != null &&
              toAccount.getCustomerProfileId() != null &&
              fromAccount.getCustomerProfileId().equals(toAccount.getCustomerProfileId()))
              ? "Internal Transfer" : "External Transfer";

      System.out.println("\n" + transactionType + ": $" + amount +
          " from account " + fromAccountNumber +
          " (Customer: " + fromAccount.getCustomerProfileId() + ") to account " +
          toAccountNumber + " (Customer: " + toAccount.getCustomerProfileId() + ")");

      // 9. Commit the transaction
      transaction.commit();
      System.out.println("Transaction completed successfully");
      System.out.println("New balance for account " + fromAccountNumber + ": $" + fromAccount.getBalance());
      System.out.println("New balance for account " + toAccountNumber + ": $" + toAccount.getBalance());

    } catch (Exception e) {
      // 10. Rollback on exception
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      System.out.println("Transaction failed: " + e.getMessage());
      e.printStackTrace();
    } finally {
      // 11. Close the session
      session.close();
    }
  }
}