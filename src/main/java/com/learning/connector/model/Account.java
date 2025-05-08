package com.learning.connector.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "accounts")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "account_number", unique = true, nullable = false)
  private String accountNumber;

  @Column(name = "balance", nullable = false)
  private BigDecimal balance;

  @Column(name = "customer_profile_id")
  private String customerProfileId;


  public Account() {
  }


  public Account(Integer id, String accountNumber, BigDecimal balance, String customerProfileId) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.customerProfileId = customerProfileId;
  }

  public Account(String accountNumber, BigDecimal balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String getCustomerProfileId() {
    return customerProfileId;
  }

  public void setCustomerProfileId(String customerProfileId) {
    this.customerProfileId = customerProfileId;
  }
}
