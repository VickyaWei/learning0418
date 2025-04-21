package com.learning.singleton;

/**
 * @author vickyaa
 * @date 4/18/25
 * @file TeacherLazy
 */
public class TeacherLazy {

  private String lastName;
  private String firstName;

  private TeacherLazy(){}

  private static class LazyHolder {
    private static final TeacherLazy instance = new TeacherLazy();
  }

  public static TeacherLazy getInstance() {
    return LazyHolder.instance;
  }
//  private TeacherLazy() {}
//
//  private static class LazyHolder {
//    private static final TeacherLazy instance = new TeacherLazy();
//  }
//
//  public static TeacherLazy getInstance() {
//    return LazyHolder.instance;
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
