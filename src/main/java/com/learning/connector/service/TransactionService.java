package com.learning.connector.service;

import java.math.BigDecimal;

public interface TransactionService {

  void transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount);
}
