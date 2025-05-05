package com.learning.connector.service;

import com.learning.connector.exception.AccountNotFoundException;
import com.learning.connector.exception.CustomerNotFoundException;
import com.learning.connector.model.Account;
import com.learning.connector.model.CustomerProfile;
import com.learning.connector.repository.AccountRepository;
import com.learning.connector.repository.CustomerProfileRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final CustomerProfileRepository customerProfileRepository;
  private final RedisMonitorService redisMonitorService;


  @Autowired
  public AccountServiceImpl(AccountRepository accountRepository,
      CustomerProfileRepository customerProfileRepository, RedisMonitorService redisMonitorService) {
    this.accountRepository = accountRepository;
    this.customerProfileRepository = customerProfileRepository;
    this.redisMonitorService = redisMonitorService;
  }


  @Override
  public Account getAccountByNumber(String accountNumber) {
    logger.info("Fetching account from database: {}", accountNumber);
    return accountRepository.findById(accountNumber)
        .orElseThrow(() -> {
          logger.warn("Account not found: {}", accountNumber);
          return new AccountNotFoundException(accountNumber);
        });
  }

  public List<Account> getAccountsForCustomer(String customerId) {
    CustomerProfile profile = customerProfileRepository.findById(customerId)
        .orElseThrow(() -> new CustomerNotFoundException(customerId));

    return accountRepository.findAllById(profile.getAccountNumbers());
  }


  @Override
  public Account updateAccount(Account account) {
    String customerId = account.getCustomerId();
    String accountId = account.getAccountNumber();
    String transactionId = "update"; // or generate some unique ID

    String lockKey = customerId + "_" + accountId + "_" + transactionId;


    if (!redisMonitorService.acquireLock(customerId, accountId, transactionId)) {
      logger.warn("Could not acquire lock for {}", lockKey);
      throw new IllegalStateException("Account update is currently locked");
    }

    try {
      Account updated = accountRepository.save(account);
      logger.info("Updated account: {}", updated.getAccountNumber());
      return updated;
    } finally {
      redisMonitorService.releaseLock(customerId, accountId, transactionId);
      logger.info("Released lock for {}", lockKey);
    }
  }

  @Override
  public void deleteAccount(String accountNumber) {
    accountRepository.deleteById(accountNumber);
  }
}
