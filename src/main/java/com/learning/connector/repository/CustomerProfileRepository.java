package com.learning.connector.repository;

import com.learning.connector.model.CustomerProfile;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file CustomerProfileRepository
 */
public interface CustomerProfileRepository extends MongoRepository<CustomerProfile, String> {
  Optional<CustomerProfile> findByAccountNumber(String accountNumber);
}
