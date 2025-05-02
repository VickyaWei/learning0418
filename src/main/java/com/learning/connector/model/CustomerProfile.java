package com.learning.connector.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vickyaa
 * @date 5/2/25
 * @file Customer
 */

@Document(collection = "customer_profiles")
public class CustomerProfile {
  @Id
  private String id;

  @Field("account_number")
  @Indexed(unique = true)
  private String accountNumber;

  @Field("first_name")
  private String firstName;

  @Field("last_name")
  private String lastName;

  @Field("email")
  private String email;

  public CustomerProfile() {
  }

  public CustomerProfile(String id, String accountNumber, String firstName, String lastName,
      String email) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
