package com.learning.connector.controller;

import com.learning.connector.model.Account;
import com.learning.connector.service.AccountService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file CacheTestController
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  // CREATE - Create a new account
  @PostMapping
  public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
    Account createdAccount = accountService.createAccount(account);
    return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
  }

  // GET - Get all accounts
  @GetMapping
  public ResponseEntity<List<Account>> getAllAccounts() {
    List<Account> accounts = accountService.getAllAccounts();
    return ResponseEntity.ok(accounts);
  }

  // GET - Get account by ID
  @GetMapping("/id/{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable Integer id) {
    Account account = accountService.getAccountById(id);
    if (account != null) {
      return ResponseEntity.ok(account);
    }
    return ResponseEntity.notFound().build();
  }

  // GET - Get account by account number
  @GetMapping("/{accountNumber}")
  public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
    Account account = accountService.getAccountByNumber(accountNumber);
    if (account != null) {
      return ResponseEntity.ok(account);
    }
    return ResponseEntity.notFound().build();
  }

  // UPDATE - Update account by account number
  @PutMapping("/{accountNumber}")
  public ResponseEntity<Account> updateAccount(
      @PathVariable String accountNumber,
      @RequestBody Account accountDetails) {

    Account account = accountService.getAccountByNumber(accountNumber);
    if (account == null) {
      return ResponseEntity.notFound().build();
    }

    // Update account with new details
    account.setBalance(accountDetails.getBalance());

    // Update other fields if provided
    if (accountDetails.getCustomerProfileId() != null) {
      account.setCustomerProfileId(accountDetails.getCustomerProfileId());
    }

    Account updatedAccount = accountService.updateAccount(account);
    return ResponseEntity.ok(updatedAccount);
  }

  @PatchMapping("/{accountNumber}/balance")
  public ResponseEntity<Account> updateAccountBalance(
      @PathVariable String accountNumber,
      @RequestParam BigDecimal newBalance) {

    Account account = accountService.getAccountByNumber(accountNumber);
    if (account == null) {
      return ResponseEntity.notFound().build();
    }

    account.setBalance(newBalance);
    Account updatedAccount = accountService.updateAccount(account);
    return ResponseEntity.ok(updatedAccount);
  }

  @DeleteMapping("/{accountNumber}")
  public ResponseEntity<Void> deleteAccount(@PathVariable String accountNumber) {
    Account account = accountService.getAccountByNumber(accountNumber);
    if (account == null) {
      return ResponseEntity.notFound().build();
    }

    accountService.deleteAccount(account);
    return ResponseEntity.noContent().build();
  }

}
