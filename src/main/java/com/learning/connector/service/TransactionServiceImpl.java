package com.learning.connector.service;

import com.learning.connector.model.Account;
import com.learning.connector.model.TransactionLog;
import com.learning.connector.repository.AccountRepository;
import com.learning.connector.repository.TransactionHistoryRepository;
import com.learning.connector.repository.TransactionLogRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

  private final AccountRepository accountRepository;
  private final TransactionLogRepository transactionLogRepository;
  private final TransactionHistoryService transactionHistoryService;

  @Autowired
  public TransactionServiceImpl(AccountRepository accountRepository,
      TransactionLogRepository transactionLogRepository,
      TransactionHistoryService transactionHistoryService) {
    this.accountRepository = accountRepository;
    this.transactionLogRepository = transactionLogRepository;
    this.transactionHistoryService = transactionHistoryService;
  }


  @Override
  @Transactional
  public void transferMoney(String fromAccount, String toAccount, BigDecimal amount) {
    // 1. Get accounts
    Account fromAccountObj = accountRepository.findByAccountNumber(fromAccount)
        .orElseThrow(() -> new RuntimeException("Source account not found: " + fromAccount));

    Account toAccountObj = accountRepository.findByAccountNumber(toAccount)
        .orElseThrow(() -> new RuntimeException("Destination account not found: " + toAccount));

    // 2. Transfer money
    fromAccountObj.setBalance(fromAccountObj.getBalance().subtract(amount));
    toAccountObj.setBalance(toAccountObj.getBalance().add(amount));

    // 3. Log the transaction
    TransactionLog log = new TransactionLog();
    log.setFromAccount(fromAccount);
    log.setToAccount(toAccount);
    log.setAmount(amount);
    log.setTransactionDate(LocalDate.now());

    // 4. Save all changes in one transaction
    accountRepository.save(fromAccountObj);
    accountRepository.save(toAccountObj);
    transactionLogRepository.save(log);

    try {
      // For the source account - money going out
      transactionHistoryService.recordTransaction(
          fromAccount,
          "TRANSFER_OUT",
          amount.negate(),
          toAccount,
          "Transfer to " + toAccount
      );

      // For the destination account - money coming in
      transactionHistoryService.recordTransaction(
          toAccount,
          "TRANSFER_IN",
          amount,
          fromAccount,
          "Transfer from " + fromAccount
      );
    } catch (Exception e) {
      // Log the error but don't fail the transaction
      System.err.println("Error recording to Cassandra: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
