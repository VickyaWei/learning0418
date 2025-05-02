package com.learning.connector.service;

import com.learning.connector.model.Account;
import com.learning.connector.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import java.util.Optional;
import org.springframework.cache.annotation.CachePut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file AccountServiceImpl
 */
@Service
public class AccountServiceImpl implements AccountService{
  private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
  private final AccountRepository accountRepository;

  @Autowired
  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Cacheable(value = "accounts", key = "#accountNumber")
  public Optional<Account> getAccountByNumber(String accountNumber) {
    // This will be cached in Redis
    logger.info("Fetching account from database: {}", accountNumber);
    return accountRepository.findById(accountNumber);
  }

  @CachePut(value = "accounts", key = "#account.accountNumber")
  public Account updateAccount(Account account) {
    return accountRepository.save(account);
  }

  @CacheEvict(value = "accounts", key = "#accountNumber")
  public void deleteAccount(String accountNumber) {
    accountRepository.deleteById(accountNumber);
  }
}
