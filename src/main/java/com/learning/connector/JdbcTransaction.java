package com.learning.connector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTransaction {
  private static final String URL = "jdbc:postgresql://localhost:5432/hibernate_learning";
  private static final String USER = "postgres";
  private static final String PASSWORD = "weixing5shishi";

  public void createTablesAndSampleData() {
    Connection connection = null;
    PreparedStatement stmt = null;

    try {
      // Load PostgreSQL JDBC driver
      Class.forName("org.postgresql.Driver");

      // Establish connection
      connection = DriverManager.getConnection(URL, USER, PASSWORD);

      // Create accounts table
      String createAccountsTable =
          "CREATE TABLE IF NOT EXISTS accounts (" +
              "id SERIAL PRIMARY KEY, " +
              "account_number VARCHAR(20) UNIQUE NOT NULL, " +
              "balance DECIMAL(10,2) NOT NULL)";

      stmt = connection.prepareStatement(createAccountsTable);
      stmt.executeUpdate();

      // Create transaction_log table
      String createTransactionLogTable =
          "CREATE TABLE IF NOT EXISTS transaction_log (" +
              "id SERIAL PRIMARY KEY, " +
              "from_account VARCHAR(20) NOT NULL, " +
              "to_account VARCHAR(20) NOT NULL, " +
              "amount DECIMAL(10,2) NOT NULL, " +
              "transaction_date TIMESTAMP NOT NULL)";

      stmt = connection.prepareStatement(createTransactionLogTable);
      stmt.executeUpdate();

      System.out.println("Tables created successfully");

      // Check if sample data already exists
      String checkSql = "SELECT COUNT(*) FROM accounts WHERE account_number IN (?, ?)";
      stmt = connection.prepareStatement(checkSql);
      stmt.setString(1, "ACC123");
      stmt.setString(2, "ACC456");

      ResultSet rs = stmt.executeQuery();
      rs.next();
      int count = rs.getInt(1);

      if (count == 0) {
        // Insert sample accounts
        String insertAccount1 = "INSERT INTO accounts (account_number, balance) VALUES (?, ?)";
        stmt = connection.prepareStatement(insertAccount1);
        stmt.setString(1, "ACC123");
        stmt.setBigDecimal(2, new BigDecimal("1000.00"));
        stmt.executeUpdate();

        String insertAccount2 = "INSERT INTO accounts (account_number, balance) VALUES (?, ?)";
        stmt = connection.prepareStatement(insertAccount2);
        stmt.setString(1, "ACC456");
        stmt.setBigDecimal(2, new BigDecimal("500.00"));
        stmt.executeUpdate();

        System.out.println("Sample data inserted successfully");
      } else {
        System.out.println("Sample data already exists");
      }

    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Failed to create tables or insert data: " + e.getMessage());
      e.printStackTrace();
    } finally {
      try {
        if (stmt != null) stmt.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void transferMoney(String fromAccount, String toAccount, BigDecimal amount) {
    Connection connection = null;

    try {
      // Load PostgreSQL JDBC driver
      Class.forName("org.postgresql.Driver");

      // 1. Establish connection
      connection = DriverManager.getConnection(URL, USER, PASSWORD);

      // 2. Turn off auto-commit to enable transaction
      connection.setAutoCommit(false);

      // 3. Prepare SQL statements using PreparedStatement
      String withdrawSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
      PreparedStatement withdrawStmt = connection.prepareStatement(withdrawSql);
      withdrawStmt.setBigDecimal(1, amount);
      withdrawStmt.setString(2, fromAccount);

      String depositSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
      PreparedStatement depositStmt = connection.prepareStatement(depositSql);
      depositStmt.setBigDecimal(1, amount);
      depositStmt.setString(2, toAccount);

      String logSql = "INSERT INTO transaction_log (from_account, to_account, amount, transaction_date) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
      PreparedStatement logStmt = connection.prepareStatement(logSql);
      logStmt.setString(1, fromAccount);
      logStmt.setString(2, toAccount);
      logStmt.setBigDecimal(3, amount);

      // 4. Execute statements
      int withdrawResult = withdrawStmt.executeUpdate();

      // Check if the withdraw was successful
      if (withdrawResult == 0) {
        throw new SQLException("Failed to withdraw from account: " + fromAccount);
      }

      int depositResult = depositStmt.executeUpdate();

      // Check if the deposit was successful
      if (depositResult == 0) {
        throw new SQLException("Failed to deposit to account: " + toAccount);
      }

      // Log the transaction
      logStmt.executeUpdate();

      // 5. Commit the transaction if all operations were successful
      connection.commit();
      System.out.println("Transaction completed successfully");

      // 6. Close resources
      withdrawStmt.close();
      depositStmt.close();
      logStmt.close();

    } catch (SQLException | ClassNotFoundException e) {
      // 7. Rollback on exception
      try {
        if (connection != null) {
          connection.rollback();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }

      System.out.println("Transaction failed: " + e.getMessage());
      e.printStackTrace();
    } finally {
      // 8. Restore auto-commit and close connection
      try {
        if (connection != null) {
          connection.setAutoCommit(true);
          connection.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    JdbcTransaction transaction = new JdbcTransaction();
    transaction.createTablesAndSampleData();
    transaction.transferMoney("ACC123", "ACC456", new BigDecimal("100.00"));
  }
}
