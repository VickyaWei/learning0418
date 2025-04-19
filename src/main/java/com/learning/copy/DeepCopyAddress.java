package com.learning.copy;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file DeepCopyAddress
 */
public class DeepCopyAddress implements Cloneable {
  String city;
  DeepCopyAddress(String city) {
    this.city = city;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
