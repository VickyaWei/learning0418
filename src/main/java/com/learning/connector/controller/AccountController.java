package com.learning.connector.controller;

import com.learning.connector.model.Account;
import com.learning.connector.service.AccountService;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/{accountNumber}")
  public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
    Account account = accountService.getAccountByNumber(accountNumber);
    return ResponseEntity.ok(account);
  }

  @PostMapping("/update/{accountNumber}")
  public ResponseEntity<String> updateAccountBalance(@PathVariable String accountNumber,
      @RequestParam BigDecimal newBalance) {
    Account account = accountService.getAccountByNumber(accountNumber);
    account.setBalance(newBalance);
    accountService.updateAccount(account);
    return ResponseEntity.ok("Account updated successfully");
  }
}
