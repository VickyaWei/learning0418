package com.learning.connector.repository;

import com.learning.connector.model.Account;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
  Optional<Account> findByAccountNumber(String accountNumber);
  void deleteByAccountNumber(String accountNumber);
  List<Account> findByCustomerProfileId(String customerProfileId);
}