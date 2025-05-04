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
@RequestMapping("/api/cache-test")
// exception handler missing
public class CacheTestController {
  private final AccountService accountService;

  @Autowired
  public CacheTestController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/account/{accountNumber}")
  public ResponseEntity<String> testAccountCache(@PathVariable String accountNumber) {
    // First call - should hit the database
    long startTime = System.currentTimeMillis();
    Optional<Account> firstResult = accountService.getAccountByNumber(accountNumber);
    long firstCallTime = System.currentTimeMillis() - startTime;

    // Second call - should hit the cache
    startTime = System.currentTimeMillis();
    Optional<Account> secondResult = accountService.getAccountByNumber(accountNumber);
    long secondCallTime = System.currentTimeMillis() - startTime;

    String response = String.format(
        "First call (DB): %d ms, account found: %s\n" +
            "Second call (Cache): %d ms, account found: %s\n" +
            "Cache performance improvement: %.2f%%",
        firstCallTime, firstResult.isPresent(),
        secondCallTime, secondResult.isPresent(),
        (100.0 * (firstCallTime - secondCallTime) / firstCallTime)
    );

    return ResponseEntity.ok(response);
  }

  @PostMapping("/update/{accountNumber}")
  public ResponseEntity<String> testCacheUpdate(@PathVariable String accountNumber,
      @RequestParam BigDecimal newBalance) {
    Optional<Account> accountOpt = accountService.getAccountByNumber(accountNumber);
    if (accountOpt.isPresent()) {
      Account account = accountOpt.get();
      account.setBalance(newBalance);
      accountService.updateAccount(account);
      return ResponseEntity.ok("Account updated and cache refreshed");
    }
    return ResponseEntity.notFound().build();
  }
}
