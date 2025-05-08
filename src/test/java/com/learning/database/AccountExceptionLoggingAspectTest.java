package com.learning.database;

import com.learning.connector.aop.AccountExceptionLoggingAspect;
import com.learning.connector.exception.AccountNotFoundException;
import com.learning.connector.service.AccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author vickyaa
 * @date 5/5/25
 * @file AccountExceptionLoggingAspectTest
 */
@SpringBootTest
public class AccountExceptionLoggingAspectTest {
  @Autowired
  private AccountService accountService;

  @BeforeEach
  public void clearLogs() {
    TestLoggerFactory.clear();
  }

  @Test
  public void testAccountNotFoundExceptionIsLogged() {
    // Get the test logger for our aspect class
    TestLogger logger = TestLoggerFactory.getTestLogger(AccountExceptionLoggingAspect.class);

    // Set up the mock to throw the exception when called
    String accountNumber = "12345";
    when(accountService.getAccountByNumber(accountNumber))
        .thenThrow(new AccountNotFoundException(accountNumber));

    // Act - trigger the exception
    try {
      accountService.getAccountByNumber(accountNumber);
      fail("Expected AccountNotFoundException to be thrown");
    } catch (AccountNotFoundException e) {
      // Expected exception
    }

    // Assert - verify the log message was generated
    assertTrue(logger.getLoggingEvents().size() > 0, "No log events were recorded");

    // Check for the error log with the right message format
    boolean foundErrorLog = false;
    for (LoggingEvent event : logger.getLoggingEvents()) {
      if (event.getLevel().toString().equals("ERROR") &&
          event.getMessage().contains("Account lookup failed") &&
          event.getMessage().contains(accountNumber)) {
        foundErrorLog = true;
        break;
      }
    }

    assertTrue(foundErrorLog, "Expected error log message was not found");
  }
}
