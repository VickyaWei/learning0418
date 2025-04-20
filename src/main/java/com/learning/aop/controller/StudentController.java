package com.learning.aop.controller;

import com.learning.springaop.model.Student;
import com.learning.springaop.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vickyaa
 * @date 4/17/25
 * @file StudentController
 */

@RestController
public class StudentController {

  @Autowired
  private StudentService studentService;

  @GetMapping(value = "/add")
  public Student addStudent(
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName) {
    return studentService.addStudent(firstName, lastName);
//    return new ResponseEntity<Student>(Student, HttpStatus.OK);
  }
}
