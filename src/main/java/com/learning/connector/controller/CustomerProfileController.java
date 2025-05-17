package com.learning.connector.controller;

import com.learning.connector.exception.AccountNotFoundException;
import com.learning.connector.model.Account;
import com.learning.connector.model.CustomerProfile;
import com.learning.connector.service.AccountService;
import com.learning.connector.service.CustomerProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vickyaa
 * @date 5/6/25
 * @file CustomerProfileController
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerProfileController {
  private static Logger logger = LoggerFactory.getLogger(CustomerProfileController.class);
  private final CustomerProfileService customerProfileService;
  private final AccountService accountService;

  @Autowired
  public CustomerProfileController(CustomerProfileService customerProfileService,
      AccountService accountService) {
    this.customerProfileService = customerProfileService;
    this.accountService = accountService;
  }

  // CREATE - Create a new customer profile
  @PostMapping
  public ResponseEntity<CustomerProfile> createCustomerProfile(@RequestBody CustomerProfile customerProfile) {
    CustomerProfile createdProfile = customerProfileService.createCustomerProfile(customerProfile);
    return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
  }


  // UPDATE - Update customer profile
  @PutMapping("/{id}")
  public ResponseEntity<CustomerProfile> updateCustomerProfile(
      @PathVariable String id,
      @RequestBody CustomerProfile profileDetails) {

    CustomerProfile profile = customerProfileService.getCustomerProfileById(id);
    if (profile == null) {
      return ResponseEntity.notFound().build();
    }

    // Update profile details
    if (profileDetails.getFirstName() != null) {
      profile.setFirstName(profileDetails.getFirstName());
    }
    if (profileDetails.getLastName() != null) {
      profile.setLastName(profileDetails.getLastName());
    }
    if (profileDetails.getEmail() != null) {
      profile.setEmail(profileDetails.getEmail());
    }

    CustomerProfile updatedProfile = customerProfileService.updateCustomerProfile(profile);
    return ResponseEntity.ok(updatedProfile);
  }

  // DELETE - Delete a customer profile
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomerProfile(@PathVariable String id) {
    CustomerProfile profile = customerProfileService.getCustomerProfileById(id);
    if (profile == null) {
      return ResponseEntity.notFound().build();
    }

    Map<String, Object> response = new HashMap<>();
    response.put("profile", profile);

    List<Account> accounts = accountService.getAccountsByCustomerProfileId(id);
    response.put("accounts", accounts);
    return ResponseEntity.noContent().build();
  }

  // ADD account to customer
  @PostMapping("/{id}/accounts")
  public ResponseEntity<Map<String, Object>> addAccountToCustomer(
      @PathVariable String id,
      @RequestBody Account account) {

    CustomerProfile profile = customerProfileService.getCustomerProfileById(id);
    if (profile == null) {
      return ResponseEntity.notFound().build();
    }

    // Create the account and link it to this customer
    account.setCustomerProfileId(id);
    Account savedAccount = accountService.createAccount(account);

    // Add the account number to the customer's list
    List<String> accountNumbers = profile.getAccountNumbers();
    if (accountNumbers == null) {
      accountNumbers = new ArrayList<>();
    }
    accountNumbers.add(savedAccount.getAccountNumber());
    profile.setAccountNumbers(accountNumbers);

    CustomerProfile updatedProfile = customerProfileService.updateCustomerProfile(profile);

    // Return both the updated profile and the new account
    Map<String, Object> response = new HashMap<>();
    response.put("profile", updatedProfile);
    response.put("newAccount", savedAccount);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  // REMOVE account from customer
  @DeleteMapping("/{id}/accounts/{accountNumber}")
  public ResponseEntity<Map<String, Object>> removeAccountFromCustomer(
      @PathVariable String id,
      @PathVariable String accountNumber) {

    CustomerProfile profile = customerProfileService.getCustomerProfileById(id);
    if (profile == null) {
      return ResponseEntity.notFound().build();
    }

    // Remove the account number from the customer's list
    List<String> accountNumbers = profile.getAccountNumbers();
    boolean accountFound = false;

    if (accountNumbers != null) {
      accountFound = accountNumbers.removeIf(accNum -> accNum.equals(accountNumber));
      if (accountFound) {
        profile.setAccountNumbers(accountNumbers);
        CustomerProfile updatedProfile = customerProfileService.updateCustomerProfile(profile);

        // Optionally delete the account as well
        accountService.deleteAccountByNumber(accountNumber);

        Map<String, Object> response = new HashMap<>();
        response.put("profile", updatedProfile);
        response.put("message", "Account removed successfully");

        return ResponseEntity.ok(response);
      }
    }

    return ResponseEntity.notFound().build();
  }


  @GetMapping
  public ResponseEntity<?> getAllCustomerProfilesWithAccounts() {
    try {
      List<Map<String, Object>> profilesWithAccounts =
          customerProfileService.getAllCustomerProfilesWithAccounts();
      return ResponseEntity.ok(profilesWithAccounts);
    } catch (Exception e) {
      e.printStackTrace();
      Map<String, String> error = new HashMap<>();
      error.put("error", "Failed to retrieve customer profiles");
      error.put("message", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getCustomerProfileWithAccounts(@PathVariable String id) {
    Map<String, Object> profileWithAccounts =
        customerProfileService.getCustomerProfileWithAccounts(id);
    if (profileWithAccounts == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(profileWithAccounts);
  }
}