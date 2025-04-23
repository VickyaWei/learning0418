package com.learning.aop.model;

/**
 * @author vickyaa
 * @date 4/22/25
 * @file StudentDoesNotExistException
 */
public class StudentDoesNotExistException extends RuntimeException {
  public StudentDoesNotExistException(String message) {
    super(message);
  }
}
