package com.learning.copy;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file Contact
 */
public class Contact implements Cloneable{
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


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String mail) {
    this.email = mail;
  }

  @Override
  public String toString() {
    return "Contact{" +
        "name='" + name + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
