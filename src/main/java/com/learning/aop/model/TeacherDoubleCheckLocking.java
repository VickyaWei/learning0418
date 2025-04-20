package com.learning.aop.model;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file TeacherDoubleCheckLocking
 */
public class TeacherDoubleCheckLocking {

  private String lastName;
  private String firstName;

  private static volatile TeacherDoubleCheckLocking instance;

  private TeacherDoubleCheckLocking() {
  }

  public static TeacherDoubleCheckLocking getInstance() {
    if(instance == null) {
      synchronized (TeacherDoubleCheckLocking.class) {
        if(instance == null) {
          instance = new TeacherDoubleCheckLocking();
        }
      }
    }
    return instance;
  }
}
