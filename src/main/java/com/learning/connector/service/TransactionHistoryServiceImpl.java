package com.learning.connector.service;

import com.learning.connector.model.TransactionHistory;
import com.learning.connector.repository.TransactionHistoryRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file TransactionHistoryServiceImpl
 */

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService{

  private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryServiceImpl.class);

  private final AccountService accountService;
  private final TransactionHistoryRepository transactionHistoryRepository;
  private final RedisMonitorService redisMonitorService;

  @Autowired
  public TransactionHistoryServiceImpl(TransactionHistoryRepository transactionHistoryRepository,
      RedisMonitorService redisMonitorService,
      AccountService accountService) {
    this.transactionHistoryRepository = transactionHistoryRepository;
    this.redisMonitorService = redisMonitorService;
    this.accountService = accountService;
  }

  @Override
  public List<TransactionHistory> getTransactionHistory(String accountNumber) {
    logger.info("Fetching all transaction history for account {}", accountNumber);
    return transactionHistoryRepository.findByAccountNumber(accountNumber);
  }

  @Override
  public TransactionHistory recordTransaction(String accountNumber, String transactionType,
      BigDecimal amount, String relatedAccount,
      String description) {
    logger.info("Recording transaction for account {}: {} {}",
        accountNumber, transactionType, amount);

    String customerId = getCustomerIdFromAccount(accountNumber);
    String transactionId = UUID.randomUUID().toString();
    String lockKey = customerId + "_" + accountNumber + "_" + transactionId;

    if (!redisMonitorService.acquireLock(customerId, accountNumber, transactionId)) {
      logger.warn("Could not acquire lock for {}", lockKey);
      throw new IllegalStateException("Transaction is currently locked");
    }

    try {
      TransactionHistory history = new TransactionHistory();
      history.setTransactionId(UUID.fromString(transactionId));
      history.setTransactionDate(LocalDate.now());
      history.setAccountNumber(accountNumber);
      history.setTransactionType(transactionType);
      history.setAmount(amount);
      history.setRelatedAccount(relatedAccount);
      history.setDescription(description);

      return transactionHistoryRepository.save(history);
    } finally {
      redisMonitorService.releaseLock(customerId, accountNumber, transactionId);
      logger.info("Released lock for {}", lockKey);
    }
  }

  private String getCustomerIdFromAccount(String accountNumber) {
    return accountService.getAccountByNumber(accountNumber).getCustomerProfileId();
  }

}
