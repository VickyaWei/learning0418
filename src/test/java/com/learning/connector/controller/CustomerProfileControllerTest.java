package com.learning.connector.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.learning.connector.model.CustomerProfile;
import com.learning.connector.service.AccountService;
import com.learning.connector.service.CustomerProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.mockito.Mockito.*;
/**
 * @author vickyaa
 * @date 5/15/25
 * @file CustomerProfileControllerTest
 */
class CustomerProfileControllerTest {
  @InjectMocks
  private CustomerProfileController controller;

  @Mock
  private CustomerProfileService customerProfileService;

  @Mock
  private AccountService accountService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateCustomerProfile_success() {
    CustomerProfile input = new CustomerProfile();
    input.setFirstName("John");
    input.setLastName("Doe");
    input.setEmail("john@example.com");
    input.setPassword("plainPassword");

    CustomerProfile encoded = new CustomerProfile();
    encoded.setFirstName("John");
    encoded.setLastName("Doe");
    encoded.setEmail("john@example.com");
    encoded.setPassword("encodedPassword");

    when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
    when(customerProfileService.createCustomerProfile(any(CustomerProfile.class))).thenReturn(encoded);

    ResponseEntity<CustomerProfile> response = controller.createCustomerProfile(input);

    assertEquals(201, response.getStatusCodeValue());
    assertEquals("encodedPassword", response.getBody().getPassword());
    verify(customerProfileService).createCustomerProfile(any(CustomerProfile.class));
  }

  @Test
  void testUpdateCustomerProfile_success() {
    String id = "123";
    CustomerProfile existing = new CustomerProfile();
    existing.setId(id);
    existing.setFirstName("Old");
    existing.setPassword("oldPassword");

    CustomerProfile updates = new CustomerProfile();
    updates.setFirstName("New");
    updates.setPassword("newPassword");

    CustomerProfile updated = new CustomerProfile();
    updated.setId(id);
    updated.setFirstName("New");
    updated.setPassword("encodedPassword");

    when(customerProfileService.getCustomerProfileById(id)).thenReturn(existing);
    when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
    when(customerProfileService.updateCustomerProfile(any(CustomerProfile.class))).thenReturn(updated);

    ResponseEntity<CustomerProfile> response = controller.updateCustomerProfile(id, updates);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals("New", response.getBody().getFirstName());
    assertEquals("encodedPassword", response.getBody().getPassword());
  }

  @Test
  void testUpdateCustomerProfile_notFound() {
    when(customerProfileService.getCustomerProfileById("missing")).thenReturn(null);

    ResponseEntity<CustomerProfile> response = controller.updateCustomerProfile("missing", new CustomerProfile());

    assertEquals(404, response.getStatusCodeValue());
    verify(customerProfileService, never()).updateCustomerProfile(any());
  }
}