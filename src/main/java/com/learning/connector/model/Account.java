package com.learning.connector.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Account implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "account_number")
  private String accountNumber;

  @Column(name = "balance")
  private BigDecimal balance;


  public Account() {
  }

  public Account(String accountNumber, BigDecimal balance){
    this.accountNumber = accountNumber;
    this.balance = balance;
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

}
