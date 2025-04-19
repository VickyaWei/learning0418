package com.learning.copy;

import java.util.ArrayList;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file ImmutablePerson
 */
public final class ImmutablePerson {
  private final String name;
  private final int age;
  private final ArrayList<String> hobbies;

  public ImmutablePerson(String name, int age, ArrayList<String> hobbies) {
    this.name = name;
    this.age = age;
    this.hobbies = new ArrayList<>(hobbies);
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public ArrayList<String> getHobbies() {
    return new ArrayList<>(hobbies);
  }
}
