package com.learning.connector.service;




// To Do

public interface RedisMonitorService {
  boolean acquireLock(String customerId, String accountId, String transactionId);
  void releaseLock(String customerId, String accountId, String transactionId);
  boolean isLocked(String customerId, String accountId, String transactionId);
}
