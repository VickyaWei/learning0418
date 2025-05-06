package com.learning.database;

import com.learning.connector.controller.AccountController;
import com.learning.connector.exception.AccountNotFoundException;
import com.learning.connector.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author vickyaa
 * @date 5/5/25
 * @file AccountControllerTest
 */

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

  private static final Logger logger = LoggerFactory.getLogger(AccountControllerTest.class);

  @Mock
  private AccountService accountService;

  @InjectMocks
  private AccountController accountController;

  @Test
  public void whenAccountNotFound_thenThrowException() {
    // Arrange
    String nonExistentAccountNumber = "12345";
    when(accountService.getAccountByNumber(nonExistentAccountNumber))
        .thenThrow(new AccountNotFoundException(nonExistentAccountNumber));

    // Act & Assert
    Exception exception = assertThrows(AccountNotFoundException.class, () -> {
      accountController.getAccount(nonExistentAccountNumber);
    });

    String expectedMessage = "Account with number " + nonExistentAccountNumber + " does not exist.";
    String actualMessage = exception.getMessage();

    logger.info("Expected message: {}", expectedMessage);
    logger.info("Actual message: {}", actualMessage);

    assertTrue(actualMessage.contains(expectedMessage));
    verify(accountService, times(1)).getAccountByNumber(nonExistentAccountNumber);
  }
}
