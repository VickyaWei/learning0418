package com.learning.hibernate.service;


import com.learning.hibernate.model.Student;
import com.learning.hibernate.repositories.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @PersistenceContext
  private EntityManager entityManager;

  public void getStudentData(Integer id) {
      Student student1 = entityManager.find(Student.class, id);
      Student student2 = entityManager.find(Student.class, id);

    System.out.println(student1 == student2);
  }
}
