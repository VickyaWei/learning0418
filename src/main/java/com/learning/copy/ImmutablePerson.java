package com.learning.copy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file ImmutablePerson
 */
public final class ImmutablePerson {
  private final String name;
  private final int age;
  // when define a data structure, always use interface
  private final List<String> hobbies;


  public ImmutablePerson(String name, int age, List<String> hobbies) {
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

  public List<String> getHobbies() {
    return new ArrayList<>(hobbies);
  }
}
