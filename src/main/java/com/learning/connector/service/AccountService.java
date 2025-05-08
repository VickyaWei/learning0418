package com.learning.connector.service;

import com.learning.connector.model.Account;
import java.util.List;
import java.util.Optional;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file AccountService
 */
public interface AccountService {

  Account createAccount(Account account);

  List<Account> getAllAccounts();

  Account getAccountById(Integer id);

  Account getAccountByNumber(String accountNumber);

  Account updateAccount(Account account);

  void deleteAccount(Account account);

  void deleteAccountByNumber(String accountNumber);

  List<Account> getAccountsByCustomerProfileId(String id);
}
