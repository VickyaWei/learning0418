package com.learning.aop.model;

/**
 * @author vickyaa
 * @date 4/17/25
 * @file Teacher
 */

// make it singleton
public class TeacherEager {

  private String lastName;
  private String firstName;

  private static TeacherEager instance = new TeacherEager();
  // inner class
  // stack heap method area
  //double check locking
  private TeacherEager() {}

  private static TeacherEager getInstance() {
    return instance;
  }

//  private static final TeacherEager instance = new TeacherEager();
//
//  private TeacherEager() {
//
//  }
//
//  public static TeacherEager getInstance() {
//    return instance;
//  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

}
