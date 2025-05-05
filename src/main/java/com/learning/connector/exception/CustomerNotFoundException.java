package com.learning.connector.exception;

public class CustomerNotFoundException extends RuntimeException {

  public CustomerNotFoundException(String customerId) {
    super(customerId);
  }
}
