package com.learning.connector.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.learning.connector.model.Account;
import com.learning.connector.service.AccountService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


import static org.mockito.Mockito.*;

/**
 * @author vickyaa
 * @date 5/15/25
 * @file AccountControllerTest
 */

class AccountControllerTest {

  @Mock
  private AccountService accountService;

  @InjectMocks
  private AccountController accountController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAccountByNumber_accountFound() {
    String accountNumber = "12345";
    Account mockAccount = new Account();
    mockAccount.setAccountNumber(accountNumber);
    mockAccount.setBalance(new BigDecimal("100.00"));

    when(accountService.getAccountByNumber(accountNumber)).thenReturn(mockAccount);

    ResponseEntity<Account> response = accountController.getAccountByNumber(accountNumber);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(accountNumber, response.getBody().getAccountNumber());
  }

  @Test
  void testGetAccountByNumber_accountNotFound() {
    String accountNumber = "99999";
    when(accountService.getAccountByNumber(accountNumber)).thenReturn(null);

    ResponseEntity<Account> response = accountController.getAccountByNumber(accountNumber);

    assertEquals(404, response.getStatusCodeValue());
  }

  @Test
  void testGetAllAccounts() {
    Account a1 = new Account();
    Account a2 = new Account();
    List<Account> mockList = Arrays.asList(a1, a2);

    when(accountService.getAllAccounts()).thenReturn(mockList);

    ResponseEntity<List<Account>> response = accountController.getAllAccounts();
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(2, response.getBody().size());
  }

  @Test
  void testCreateAccount() {
    Account newAccount = new Account();
    newAccount.setAccountNumber("111");
    newAccount.setBalance(new BigDecimal("50.00"));

    when(accountService.createAccount(any(Account.class))).thenReturn(newAccount);

    ResponseEntity<Account> response = accountController.createAccount(newAccount);

    assertEquals(201, response.getStatusCodeValue());
    assertEquals("111", response.getBody().getAccountNumber());
  }

  @Test
  void testUpdateAccount_accountExists() {
    String accountNumber = "123";
    Account existing = new Account();
    existing.setAccountNumber(accountNumber);
    existing.setBalance(new BigDecimal("100.00"));

    Account updateRequest = new Account();
    updateRequest.setBalance(new BigDecimal("200.00"));

    when(accountService.getAccountByNumber(accountNumber)).thenReturn(existing);
    when(accountService.updateAccount(any(Account.class))).thenReturn(existing);

    ResponseEntity<Account> response = accountController.updateAccount(accountNumber, updateRequest);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(new BigDecimal("200.00"), response.getBody().getBalance());
  }

  @Test
  void testUpdateAccount_accountNotExists() {
    String accountNumber = "999";
    when(accountService.getAccountByNumber(accountNumber)).thenReturn(null);

    ResponseEntity<Account> response = accountController.updateAccount(accountNumber, new Account());
    assertEquals(404, response.getStatusCodeValue());
  }

  @Test
  void testDeleteAccount_accountExists() {
    String accountNumber = "456";
    Account acc = new Account();
    acc.setAccountNumber(accountNumber);

    when(accountService.getAccountByNumber(accountNumber)).thenReturn(acc);
    doNothing().when(accountService).deleteAccount(acc);

    ResponseEntity<Void> response = accountController.deleteAccount(accountNumber);
    assertEquals(204, response.getStatusCodeValue());
  }

  @Test
  void testDeleteAccount_accountNotExists() {
    String accountNumber = "888";
    when(accountService.getAccountByNumber(accountNumber)).thenReturn(null);

    ResponseEntity<Void> response = accountController.deleteAccount(accountNumber);
    assertEquals(404, response.getStatusCodeValue());
  }
}