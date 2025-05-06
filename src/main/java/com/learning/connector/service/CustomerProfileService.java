package com.learning.connector.service;

import com.learning.connector.model.Account;
import com.learning.connector.model.CustomerProfile;
import java.util.List;
import java.util.Map;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file CustomerProfileService
 */
public interface CustomerProfileService {

  CustomerProfile createCustomerProfile(CustomerProfile customerProfile);

  List<CustomerProfile> getAllCustomerProfiles();

  CustomerProfile getCustomerProfileById(String id);

  CustomerProfile updateCustomerProfile(CustomerProfile profile);

  void deleteCustomerProfile(String id);

  // left join methods
  List<Map<String, Object>> getAllCustomerProfilesWithAccounts();
  Map<String, Object> getCustomerProfileWithAccounts(String id);
}