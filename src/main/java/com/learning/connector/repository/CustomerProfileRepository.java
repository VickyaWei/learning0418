package com.learning.connector.repository;

import com.learning.connector.model.CustomerProfile;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file CustomerProfileRepository
 */
public interface CustomerProfileRepository extends MongoRepository<CustomerProfile, String> {
  @Query("{ 'accountNumbers' : ?0 }")
  Optional<CustomerProfile> findByAccountNumber(String accountNumber);
}
