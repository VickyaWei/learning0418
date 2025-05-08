package com.learning.connector.service;




// To Do

public interface RedisMonitorService {
  // attempt to obtain a locl
  boolean acquireLock(String customerId, String accountId, String transactionId);

  // release a previously acquired lock
  void releaseLock(String customerId, String accountId, String transactionId);

  // check if the resource is currently locked
  boolean isLocked(String customerId, String accountId, String transactionId);
}
