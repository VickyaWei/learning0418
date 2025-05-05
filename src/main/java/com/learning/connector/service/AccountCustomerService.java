package com.learning.connector.service;

import com.learning.connector.model.Account;
import com.learning.connector.model.CustomerProfile;
import com.learning.connector.repository.AccountRepository;
import com.learning.connector.repository.CustomerProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * @author vickyaa
 * @date 5/5/25
 * @file AccountCustomerService
 */
@Service
public class AccountCustomerService {
  private final AccountRepository accountRepository;
  private final CustomerProfileRepository customerProfileRepository;


  public AccountCustomerService(AccountRepository accountRepository,
      CustomerProfileRepository customerProfileRepository) {
    this.accountRepository = accountRepository;
    this.customerProfileRepository = customerProfileRepository;
  }

  @Transactional
  public void linkAccountToCustomer(String accountNumber, String customerId) {
    // Update PostgreSQL side
    Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    account.setCustomerProfileId(customerId);
    accountRepository.save(account);

    // Update MongoDB side
    CustomerProfile profile = customerProfileRepository.findById(customerId)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

    if (!profile.getAccountNumbers().contains(accountNumber)) {
      profile.getAccountNumbers().add(accountNumber);
      customerProfileRepository.save(profile);
    }
  }
}
