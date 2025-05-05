package com.learning.connector.service;

import com.learning.connector.model.CustomerProfile;
import com.learning.connector.repository.CustomerProfileRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file CustomerProfileServiceImpl
 */
@Service
public class CustomerProfileServiceImpl implements CustomerProfileService{

  private final CustomerProfileRepository customerProfileRepository;

  @Autowired
  public CustomerProfileServiceImpl(CustomerProfileRepository customerProfileRepository) {
    this.customerProfileRepository = customerProfileRepository;
  }

  @Override
  public Optional<CustomerProfile> findByAccountNumber(String accountNumber) {
    return customerProfileRepository.findByAccountNumber(accountNumber);
  }

}
