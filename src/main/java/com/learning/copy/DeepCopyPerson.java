package com.learning.copy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file DeepCopyPerson
 */
public class DeepCopyPerson implements Cloneable {

  String name;
  int age;
  DeepCopyAddress deepcopyAddress;
  List<Contact> contacts;

  DeepCopyPerson(String name, int age, DeepCopyAddress deepCopyAddress) {
    this.name = name;
    this.age = age;
    this.deepcopyAddress = deepCopyAddress;
  }

  public List<Contact> getContacts() throws CloneNotSupportedException {
    List<Contact> contacts = new ArrayList<>();
    for(Contact c : this.contacts){
      contacts.add(c.clone());
    }
    return contacts;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    DeepCopyPerson cloned = (DeepCopyPerson) super.clone();
    cloned.contacts = new ArrayList<>();
    for(Contact contact : this.contacts){
      cloned.contacts.add(contact.clone());
    }
    cloned.deepcopyAddress = (DeepCopyAddress) this.deepcopyAddress.clone();
    return cloned;
  }



  @Override
  public String toString() {
    return "DeepCopyPerson{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", deepcopyAddress=" + deepcopyAddress.city +
        '}';
  }

  public static void main(String[] args) {
    try {
      DeepCopyAddress deepCopyAddress = new DeepCopyAddress("New York");
      DeepCopyPerson original = new DeepCopyPerson("Alice", 30, deepCopyAddress);
      DeepCopyPerson cloned = (DeepCopyPerson) original.clone();
      System.out.println("Original: " + original);
      System.out.println("Cloned: " + cloned);

      System.out.println("-------------");
      cloned.deepcopyAddress.city = "Los Angeles";
      System.out.println("Original: " + original);
      System.out.println("Cloned: " + cloned);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }
}
