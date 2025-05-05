package com.learning.connector.service;


import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMonitorServiceImpl implements RedisMonitorService{

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  private String buildKey(String customerId, String accountId, String transactionId) {
    return customerId + "_" + accountId + "_" + transactionId;
  }

  @Override
  public boolean acquireLock(String customerId, String accountId, String transactionId) {
    String key = buildKey(customerId, accountId, transactionId);
    Boolean success = redisTemplate.opsForValue().setIfAbsent(key, "1", 5, TimeUnit.MINUTES);
    return Boolean.TRUE.equals(success);
  }

  @Override
  public void releaseLock(String customerId, String accountId, String transactionId) {
    String key = buildKey(customerId, accountId, transactionId);
    redisTemplate.opsForValue().set(key, "0");
  }

  @Override
  public boolean isLocked(String customerId, String accountId, String transactionId) {
    String key = buildKey(customerId, accountId, transactionId);
    String value = redisTemplate.opsForValue().get(key);
    return "1".equals(value);
  }
}
