package com.learning.connector.service;

import com.learning.connector.exception.AccountNotFoundException;
import com.learning.connector.exception.CustomerNotFoundException;
import com.learning.connector.model.Account;
import com.learning.connector.model.CustomerProfile;
import com.learning.connector.model.ErrorResponse;
import com.learning.connector.repository.AccountRepository;
import com.learning.connector.repository.CustomerProfileRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file AccountServiceImpl
 */
@Service
public class AccountServiceImpl implements AccountService {
  private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
  private final AccountRepository accountRepository;
  private final CustomerProfileRepository customerProfileRepository;

  @Autowired
  public AccountServiceImpl(AccountRepository accountRepository,
      CustomerProfileRepository customerProfileRepository) {
    this.accountRepository = accountRepository;
    this.customerProfileRepository = customerProfileRepository;
  }

  @Override
  @Transactional
  public Account createAccount(Account account) {
    logger.info("Creating new account with account number: {}", account.getAccountNumber());

    // Check if the account number already exists
    if (accountRepository.findByAccountNumber(account.getAccountNumber()).isPresent()) {
      logger.error("Account with number {} already exists", account.getAccountNumber());
      throw new IllegalArgumentException("Account with this account number already exists");
    }

    // If customer profile ID is provided, verify it exists
    if (account.getCustomerProfileId() != null) {
      Optional<CustomerProfile> customerProfile = customerProfileRepository.findById(account.getCustomerProfileId());
      if (customerProfile.isEmpty()) {
        logger.error("Customer profile with ID {} not found", account.getCustomerProfileId());
        throw new CustomerNotFoundException("Customer profile not found");
      }

      // Add the account number to the customer's list
      CustomerProfile profile = customerProfile.get();
      if (profile.getAccountNumbers() == null) {
        profile.setAccountNumbers(new java.util.ArrayList<>());
      }
      profile.getAccountNumbers().add(account.getAccountNumber());
      customerProfileRepository.save(profile);
    }

    return accountRepository.save(account);
  }

  @Override
  public List<Account> getAllAccounts() {
    logger.info("Retrieving all accounts");
    return accountRepository.findAll();
  }

  @Override
  public Account getAccountById(Integer id) {
    logger.info("Retrieving account with ID: {}", id);
    return accountRepository.findById(id)
        .orElseThrow(() -> {
          logger.error("Account with ID {} not found", id);
          return new AccountNotFoundException("Account not found with id: " + id);
        });
  }

  @Override
  public Account getAccountByNumber(String accountNumber) {
    logger.info("Retrieving account with account number: {}", accountNumber);
    return accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> {
          logger.error("Account with number {} not found", accountNumber);
          return new AccountNotFoundException("Account not found with account number: " + accountNumber);
        });
  }

  @Override
  @Transactional
  public Account updateAccount(Account account) {
    logger.info("Updating account with ID: {}", account.getId());

    // Check if account exists
    Account existingAccount = accountRepository.findById(account.getId())
        .orElseThrow(() -> {
          logger.error("Account with ID {} not found for update", account.getId());
          return new AccountNotFoundException("Account not found with id: " + account.getId());
        });

    // If customer profile ID is changing, update the references
    if (account.getCustomerProfileId() != null &&
        !account.getCustomerProfileId().equals(existingAccount.getCustomerProfileId())) {

      // Remove from old customer profile if it exists
      if (existingAccount.getCustomerProfileId() != null) {
        Optional<CustomerProfile> oldProfile = customerProfileRepository.findById(
            existingAccount.getCustomerProfileId());
        if (oldProfile.isPresent()) {
          CustomerProfile profile = oldProfile.get();
          if (profile.getAccountNumbers() != null) {
            profile.getAccountNumbers().remove(account.getAccountNumber());
            customerProfileRepository.save(profile);
          }
        }
      }

      // Add to new customer profile
      Optional<CustomerProfile> newProfile = customerProfileRepository.findById(
          account.getCustomerProfileId());
      if (newProfile.isEmpty()) {
        logger.error("New customer profile with ID {} not found", account.getCustomerProfileId());
        throw new CustomerNotFoundException("New customer profile not found");
      }

      CustomerProfile profile = newProfile.get();
      if (profile.getAccountNumbers() == null) {
        profile.setAccountNumbers(new java.util.ArrayList<>());
      }
      if (!profile.getAccountNumbers().contains(account.getAccountNumber())) {
        profile.getAccountNumbers().add(account.getAccountNumber());
        customerProfileRepository.save(profile);
      }
    }

    return accountRepository.save(account);
  }

  @Override
  @Transactional
  public void deleteAccount(Account account) {
    logger.info("Deleting account with ID: {}", account.getId());
    deleteAccountByNumber(account.getAccountNumber());
  }

  @Override
  @Transactional
  public void deleteAccountByNumber(String accountNumber) {
    logger.info("Deleting account with account number: {}", accountNumber);

    Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> {
          logger.error("Account with number {} not found for deletion", accountNumber);
          return new AccountNotFoundException("Account not found with account number: " + accountNumber);
        });

    // Remove the account reference from customer profile
    if (account.getCustomerProfileId() != null) {
      Optional<CustomerProfile> customerProfile = customerProfileRepository.findById(
          account.getCustomerProfileId());
      if (customerProfile.isPresent()) {
        CustomerProfile profile = customerProfile.get();
        if (profile.getAccountNumbers() != null) {
          profile.getAccountNumbers().remove(accountNumber);
          customerProfileRepository.save(profile);
        }
      }
    }

    accountRepository.delete(account);
  }

  @Override
  public List<Account> getAccountsByCustomerProfileId(String customerProfileId) {
    logger.info("Retrieving accounts for customer profile ID: {}", customerProfileId);
    return accountRepository.findByCustomerProfileId(customerProfileId);
  }

}