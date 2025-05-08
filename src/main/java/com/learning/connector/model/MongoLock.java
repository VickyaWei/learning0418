package com.learning.connector.model;

import jakarta.persistence.Id;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author vickyaa
 * @date 5/5/25
 * @file MongoLock
 */

@Document(collection = "distributed_locks")
public class MongoLock {
  @Id
  private String id;
  private String status;
  private Date expiresAt;
}
