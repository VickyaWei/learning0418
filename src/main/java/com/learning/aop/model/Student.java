package com.learning.aop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vickyaa
 * @date 4/17/25
 * @file Student
 */

// make it immutable
  // access modifier
public final class Student {

  private final String firstName;
  private final String lastName;

  private final List<Integer> scores;


  public Student(String firstName, String lastName, List<Integer> scores) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.scores = new ArrayList<>(scores);
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public List<Integer> getScores() {
    return new ArrayList<>(scores);
  }
}
