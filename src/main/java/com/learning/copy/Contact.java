package com.learning.copy;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file Contact
 */
public class Contact {
  private String name;
  private String email;

  public Contact(String name, String email) {
    this.name = name;
    this.email = email;
  }

  @Override
  public Contact clone() throws CloneNotSupportedException {
    return (Contact) super.clone();
  }
}
