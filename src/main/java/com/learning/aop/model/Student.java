package com.learning.aop.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

  public static void main(String[] args) {
    List<Integer> scores = new LinkedList<>();
    scores.add(10);
    scores.add(20);
    scores.add(30);
    scores.get(1);

    Set<Student> students = new HashSet<>();
    students.add(new Student("Vicky", "AA", scores));
    students.add(new Student("Vicky", "AA", scores));
    students.add(new Student("1", "1", scores));
    students.add(new Student("2", "2", scores));
    System.out.println(students.size());


    Set<Student> students2 = new TreeSet<>();
    Exception e = new Exception("Exception");
    RuntimeException re = new RuntimeException("RuntimeException", e);
    e.printStackTrace();
  }

}
