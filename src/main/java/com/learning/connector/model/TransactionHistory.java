package com.learning.connector.model;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
/**
 * @author vickyaa
 * @date 5/2/25
 * @file TransactionHistory
 */

@Table("transaction_history")
public class TransactionHistory {
  // Partition key - group transactions by account
  @PrimaryKeyColumn(name = "account_number", type = PrimaryKeyType.PARTITIONED)
  private String accountNumber;

  // Clustering column - sort by date descending
  @PrimaryKeyColumn(name = "transaction_date", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
  private LocalDate transactionDate;

  // Clustering column - ensure uniqueness
  @PrimaryKeyColumn(name = "transaction_id", type = PrimaryKeyType.CLUSTERED)
  private UUID transactionId;

  @Column("transaction_type")
  private String transactionType;

  @Column("amount")
  private BigDecimal amount;

  @Column("related_account")
  private String relatedAccount;

  @Column("description")
  private String description;

  public TransactionHistory(UUID transactionId, LocalDate transactionDate) {
    this.transactionId = transactionId;
    this.transactionDate = transactionDate;
  }

  public TransactionHistory() {

  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(UUID transactionId) {
    this.transactionId = transactionId;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getRelatedAccount() {
    return relatedAccount;
  }

  public void setRelatedAccount(String relatedAccount) {
    this.relatedAccount = relatedAccount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
