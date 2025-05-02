package com.learning.connector.repository;

import com.learning.connector.model.TransactionHistory;
import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file TransctionHistoryRepository
 */
public interface TransactionHistoryRepository extends CassandraRepository<TransactionHistory, UUID> {
  List<TransactionHistory> findByAccountNumber(String accountNumber);
}
