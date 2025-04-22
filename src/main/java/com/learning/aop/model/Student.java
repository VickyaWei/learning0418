package com.learning.aop.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
public final class Student implements Comparable<Student>{

  private final String firstName;
  private final String lastName;
  private final int age;
  private final List<Integer> scores;


  public Student(String firstName, String lastName, int age, List<Integer> scores) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.scores = new ArrayList<>(scores);
  }


  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getAge() {
    return age;
  }

  public List<Integer> getScores() {
    return new ArrayList<>(scores);
  }

  @Override
  public String toString() {
    return "Student{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", age=" + age +
        ", scores=" + scores +
        '}';
  }

  @Override
  public int compareTo(Student other) {
    return Integer.compare(this.age, other.age);
  }

  public static Comparator<Student> sortByFirstName = new Comparator<Student>() {

    @Override
    public int compare(Student o1, Student o2) {
      return o1.getFirstName().compareTo(o2.getFirstName());
    }
  };


  public static Student findStudentByFirstName(Set<Student> students, String firstName) {
    for(Student student : students) {
      if(student.getFirstName().equals(firstName)) {
        return student;
      }
      throw new StudentDoesNotExistException("Student with first name '" + firstName + "' does not exist");
    }
    return null;
  }

  public static void main(String[] args) {
    List<Integer> scores = new LinkedList<>();
    scores.add(10);
    scores.add(20);
    scores.add(30);
    scores.get(1);

    Set<Student> students = new HashSet<>();
    students.add(new Student("Vicky", "W", 11, scores));
    students.add(new Student("Jason", "X", 34, scores));
    students.add(new Student("Tony", "Y", 33, scores));
    students.add(new Student("Neo", "Z", 44, scores));
    //System.out.println(students.size());

    Set<Student> studentLinkedHashSetSet = new LinkedHashSet<>();
    studentLinkedHashSetSet.add(new Student("Vicky", "W", 11, scores));
    studentLinkedHashSetSet.add(new Student("Jason", "X", 22, scores));
    studentLinkedHashSetSet.add(new Student("Tony", "Y", 33, scores));
    studentLinkedHashSetSet.add(new Student("Neo", "Z", 44, scores));
    //System.out.println(studentLinkedHashSetSet);

    // default sort based on age, based on the firstname (Comparable and Comparator)
    Set<Student> studentSortByFirstName = new TreeSet<>(Student.sortByFirstName);
    Set<Student> studentSortByAge = new TreeSet<>();

    studentSortByFirstName.addAll(students);
    studentSortByAge.addAll(students);
    //System.out.println(studentSortByFirstName);
    //System.out.println(studentSortByAge);

    try {
      Student foundStudent = Student.findStudentByFirstName(students, "Vicky");
      System.out.println(foundStudent);

      Student notFoundStudent = Student.findStudentByFirstName(students, "haha");
      System.out.println(notFoundStudent);
    } catch (StudentDoesNotExistException e) {
      System.out.println("Exception" + e.getMessage());
    } catch (Exception e) {
      System.out.println("Unexpected Exception" + e.getMessage());
    } finally {
      System.out.println("Finally block");
    }

//    Exception e = new Exception("Exception");
//    RuntimeException re = new RuntimeException("RuntimeException", e);
//    e.printStackTrace();
  }

  //Student does not exist exception define it's a checked exception or unchecked exception
}
