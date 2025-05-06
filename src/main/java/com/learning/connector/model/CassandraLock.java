package com.learning.connector.model;

import jakarta.persistence.Table;
import java.util.Date;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

/**
 * @author vickyaa
 * @date 5/5/25
 * @file CassandraLock
 */

@Table(schema = "distributed_locks")
public class CassandraLock {
  @PrimaryKey
  private String id;
  private String status;
  private Date expiresAt;
}
