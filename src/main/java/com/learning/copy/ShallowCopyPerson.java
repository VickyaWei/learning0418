package com.learning.copy;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file Person
 */
public class ShallowCopyPerson implements Cloneable {

  String name;
  int age;
  ShallowCopyAddress shallowCopyAddress;

  public ShallowCopyPerson(String name, int age, ShallowCopyAddress shallowCopyAddress) {
    this.name = name;
    this.age = age;
    this.shallowCopyAddress = shallowCopyAddress;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    return "ClonePerson{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", address=" + shallowCopyAddress.city +
        '}';
  }

  public static void main(String[] args) {
    try {
      ShallowCopyAddress shallowCopyAddress = new ShallowCopyAddress("New York");
      ShallowCopyPerson original = new ShallowCopyPerson("Alice", 30, shallowCopyAddress);
      ShallowCopyPerson cloned = (ShallowCopyPerson)original.clone();

      System.out.println("Original: " + original);
      System.out.println("Cloned: " + cloned);

      System.out.println("---------------------");
      // Modify the address
      cloned.shallowCopyAddress.city = "Los Angeles";
      System.out.println("Original: " + original);
      System.out.println("Cloned: " + cloned);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }
}
