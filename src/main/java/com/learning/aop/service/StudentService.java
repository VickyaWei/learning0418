package com.learning.aop.service;

import com.learning.aop.model.Student;
import org.springframework.stereotype.Service;

/**
 * @author vickyaa
 * @date 4/17/25
 * @file StudentService
 */

@Service
public interface StudentService {

 Student addStudent(String firstName, String lastName);
}
