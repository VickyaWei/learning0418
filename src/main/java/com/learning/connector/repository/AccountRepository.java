package com.learning.connector.repository;

import com.learning.connector.model.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, String> {
  Optional<Account> findByAccountNumber(String accountNumber);
}
