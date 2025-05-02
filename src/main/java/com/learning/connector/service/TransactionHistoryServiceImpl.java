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

  private final TransactionHistoryRepository transactionHistoryRepository;

  @Autowired
  public TransactionHistoryServiceImpl(TransactionHistoryRepository transactionHistoryRepository) {
    this.transactionHistoryRepository = transactionHistoryRepository;
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

    TransactionHistory history = new TransactionHistory();
    // Use the default constructor if available, or set these explicitly
    history.setTransactionId(UUID.randomUUID());
    history.setTransactionDate(LocalDate.now());

    // Set the transaction details
    history.setAccountNumber(accountNumber);
    history.setTransactionType(transactionType);
    history.setAmount(amount);
    history.setRelatedAccount(relatedAccount);
    history.setDescription(description);

    // Save to Cassandra
    return transactionHistoryRepository.save(history);
  }
}
