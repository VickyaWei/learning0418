package com.learning.connector.service;

import com.learning.connector.model.CustomerProfile;
import java.util.List;
import java.util.Optional;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file CustomerProfileService
 */
public interface CustomerProfileService {
  Optional<CustomerProfile> findByAccountNumber(String accountNumber);
}
