package com.learning.connector.service;

import com.learning.connector.model.TransactionHistory;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file TransactionHistoryService
 */
public interface TransactionHistoryService {

  List<TransactionHistory> getTransactionHistory(String accountNumber);

  TransactionHistory recordTransaction(String accountNumber, String transactionType,
      BigDecimal amount, String relatedAccount,
      String description);

}
