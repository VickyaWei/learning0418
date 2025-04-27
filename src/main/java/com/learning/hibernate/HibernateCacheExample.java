package com.learning.hibernate;

import com.learning.hibernate.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateCacheExample {
  public static void main(String[] args) {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    // First Session
    Session session1 = sessionFactory.openSession();
    Student student1 = session1.get(Student.class, 1L);
    System.out.println(student1.getFirstName());
    session1.close();  // First Level Cache Cleared

    // Second Session
    Session session2 = sessionFactory.openSession();
    Student student2 = session2.get(Student.class, 1L);  // Will not hit DB
    System.out.println(student2.getFirstName());
    session2.close();
  }
}