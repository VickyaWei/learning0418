package com.learning.connector.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;


/**
 * @author vickyaa
 * @date 5/5/25
 * @file PostgresLock
 */
@Entity
@Table(name = "distributed_locks")
public class PostgresLock {

  @Id
  private String id;
  private String status;
  private Timestamp expiresAt;
}
