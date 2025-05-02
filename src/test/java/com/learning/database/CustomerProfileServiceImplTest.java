package com.learning.database;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.learning.connector.model.CustomerProfile;
import com.learning.connector.repository.CustomerProfileRepository;
import com.learning.connector.service.CustomerProfileServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file CustomerProfileServiceImplTest
 */
@ExtendWith(MockitoExtension.class)
public class CustomerProfileServiceImplTest {
  @Mock
  private CustomerProfileRepository customerProfileRepository;

  @InjectMocks
  private CustomerProfileServiceImpl customerProfileService;

  private CustomerProfile sampleCustomerProfile;
  private final String ACCOUNT_NUMBER = "ACC123";

  @BeforeEach
  void setUp() {
    // Setup a sample CustomerProfile for testing
    sampleCustomerProfile = new CustomerProfile();
    sampleCustomerProfile.setId("testId123");
    sampleCustomerProfile.setAccountNumber(ACCOUNT_NUMBER);
    sampleCustomerProfile.setFirstName("John");
    sampleCustomerProfile.setLastName("Doe");
    sampleCustomerProfile.setEmail("john.doe@example.com");
  }

  @Test
  void testFindByAccountNumber_whenAccountExists_shouldReturnCustomerProfile() {
    // Arrange
    when(customerProfileRepository.findByAccountNumber(ACCOUNT_NUMBER))
        .thenReturn(Optional.of(sampleCustomerProfile));

    // Act
    Optional<CustomerProfile> result = customerProfileService.findByAccountNumber(ACCOUNT_NUMBER);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(ACCOUNT_NUMBER, result.get().getAccountNumber());
    assertEquals("John", result.get().getFirstName());
    assertEquals("john.doe@example.com", result.get().getEmail()); // Match exactly what's in your setUp method

    // Verify the repository method was called with the correct parameter
    verify(customerProfileRepository).findByAccountNumber(ACCOUNT_NUMBER);
  }
}
