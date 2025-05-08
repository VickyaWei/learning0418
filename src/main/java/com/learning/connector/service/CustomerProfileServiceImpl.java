package com.learning.connector.service;

import com.learning.connector.exception.CustomerNotFoundException;
import com.learning.connector.model.Account;
import com.learning.connector.model.CustomerProfile;
import com.learning.connector.repository.AccountRepository;
import com.learning.connector.repository.CustomerProfileRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file CustomerProfileServiceImpl
 */
@Service
public class CustomerProfileServiceImpl implements CustomerProfileService {
  private static final Logger logger = LoggerFactory.getLogger(CustomerProfileServiceImpl.class);
  private final CustomerProfileRepository customerProfileRepository;
  private final AccountRepository accountRepository;
  private final AccountService accountService;

  @Autowired
  public CustomerProfileServiceImpl(CustomerProfileRepository customerProfileRepository,
      AccountRepository accountRepository, AccountService accountService) {
    this.customerProfileRepository = customerProfileRepository;
    this.accountRepository = accountRepository;
    this.accountService = accountService;
  }

  @Override
  @Transactional
  public CustomerProfile createCustomerProfile(CustomerProfile customerProfile) {
    logger.info("Creating new customer profile with email: {}", customerProfile.getEmail());
    return customerProfileRepository.save(customerProfile);
  }

  @Override
  public List<CustomerProfile> getAllCustomerProfiles() {
    logger.info("Retrieving all customer profiles");
    return customerProfileRepository.findAll();
  }

  @Override
  public CustomerProfile getCustomerProfileById(String id) {
    logger.info("Retrieving customer profile with ID: {}", id);
    return customerProfileRepository.findById(id)
        .orElseThrow(() -> {
          logger.error("Customer profile with ID {} not found", id);
          return new CustomerNotFoundException("Customer profile not found with id: " + id);
        });
  }

  @Override
  @Transactional
  public CustomerProfile updateCustomerProfile(CustomerProfile customerProfile) {
    logger.info("Updating customer profile with ID: {}", customerProfile.getId());

    // Check if the profile exists
    if (!customerProfileRepository.existsById(customerProfile.getId())) {
      logger.error("Customer profile with ID {} not found for update", customerProfile.getId());
      throw new CustomerNotFoundException("Customer profile not found with id: " + customerProfile.getId());
    }

    return customerProfileRepository.save(customerProfile);
  }

  @Override
  @Transactional
  public void deleteCustomerProfile(String id) {
    logger.info("Deleting customer profile with ID: {}", id);

    CustomerProfile profile = getCustomerProfileById(id);

    // Delete all associated accounts
    if (profile.getAccountNumbers() != null) {
      for (String accountNumber : profile.getAccountNumbers()) {
        try {
          accountRepository.deleteByAccountNumber(accountNumber);
        } catch (Exception e) {
          logger.warn("Failed to delete account with number: {}", accountNumber, e);
        }
      }
    }

    customerProfileRepository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Map<String, Object>> getAllCustomerProfilesWithAccounts() {
    List<CustomerProfile> profiles = getAllCustomerProfiles();
    List<Map<String, Object>> profilesWithAccounts = new ArrayList<>();

    for (CustomerProfile profile : profiles) {
      Map<String, Object> profileData = new HashMap<>();
      profileData.put("profile", profile);

      // Get accounts directly using the customer profile ID
      List<Account> accounts = accountService.getAccountsByCustomerProfileId(profile.getId());
      profileData.put("accounts", accounts);

      profilesWithAccounts.add(profileData);
    }

    return profilesWithAccounts;
  }

  @Override
  @Transactional(readOnly = true)
  public Map<String, Object> getCustomerProfileWithAccounts(String id) {
    CustomerProfile profile = getCustomerProfileById(id);
    if (profile == null) {
      return null;
    }

    Map<String, Object> response = new HashMap<>();
    response.put("profile", profile);

    // Get accounts directly using the customer profile ID
    List<Account> accounts = accountService.getAccountsByCustomerProfileId(profile.getId());
    response.put("accounts", accounts);

    return response;
  }
}