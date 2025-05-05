package com.learning.connector.service;

import com.learning.connector.model.Account;
import java.util.Optional;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file AccountService
 */
public interface AccountService {
  Account getAccountByNumber(String accountNumber);
  Account updateAccount(Account account);
  void deleteAccount(String accountNumber);
}
