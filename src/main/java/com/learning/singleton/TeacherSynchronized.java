package com.learning.singleton;

/**
 * @author vickyaa
 * @date 4/20/25
 * @file TeacherSynchronized
 */
public class TeacherSynchronized {

  private String lastName;
  private String firstName;

  private static volatile TeacherSynchronized instance;

  private TeacherSynchronized() {
  }

  synchronized public static TeacherSynchronized getInstance() {
    if(instance == null) {
      instance = new TeacherSynchronized();
    }
    return instance;
  }

  public synchronized String getLastName() {
    return lastName;
  }

  public synchronized void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public synchronized String getFirstName() {
    return firstName;
  }

  public synchronized void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public static void setInstance(TeacherSynchronized instance) {
    TeacherSynchronized.instance = instance;
  }
}
