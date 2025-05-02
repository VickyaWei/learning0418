package com.learning.connector.controller;

import com.learning.connector.model.CustomerProfile;
import com.learning.connector.repository.CustomerProfileRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file CustomerProfileController
 */

@RestController
@RequestMapping("/api/customers")
public class CustomerProfileController {
  private final CustomerProfileRepository customerProfileRepository;

  @Autowired
  public CustomerProfileController(CustomerProfileRepository customerProfileRepository) {
    this.customerProfileRepository = customerProfileRepository;
  }

  @PostMapping
  public ResponseEntity<CustomerProfile> createCustomer(@RequestBody CustomerProfile customerProfile) {
    CustomerProfile savedCustomer = customerProfileRepository.save(customerProfile);
    return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<CustomerProfile>> getAllCustomers() {
    List<CustomerProfile> customers = customerProfileRepository.findAll();
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerProfile> getCustomerById(@PathVariable String id) {
    Optional<CustomerProfile> customerOpt = customerProfileRepository.findById(id);
    return customerOpt
        .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/account/{accountNumber}")
  public ResponseEntity<CustomerProfile> getCustomerByAccountNumber(@PathVariable String accountNumber) {
    Optional<CustomerProfile> customerOpt = customerProfileRepository.findByAccountNumber(accountNumber);
    return customerOpt
        .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerProfile> updateCustomer(@PathVariable String id, @RequestBody CustomerProfile customerProfile) {
    if (!customerProfileRepository.existsById(id)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    customerProfile.setId(id);
    CustomerProfile updatedCustomer = customerProfileRepository.save(customerProfile);
    return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
    if (!customerProfileRepository.existsById(id)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    customerProfileRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
