package com.learning.hibernate;

import org.hibernate.Transaction;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HibernateApplication {

  public static void main(String[] args) {
    SpringApplication.run(HibernateApplication.class, args);

    Student student = new Student();
    student.setId(1L);
    student.setFirstName("hi");
    student.setLastName("hello");

		// Configures Hibernate to work with Student Entity
    Configuration config = new Configuration().addAnnotatedClass(Student.class);
		config.configure("hibernate.cfg.xml");

		// Set up Hibernate infrastructure
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
        .applySettings(config.getProperties())
        .build();

		// Use try-with-resources for SessionFactory
		try (SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry)) {

			// Use try-with-resources for Session
			try (Session session = sessionFactory.openSession()) {

				// Start transaction
				Transaction transaction = session.beginTransaction();

				try {
					// Save the student object
					session.save(student);

					// Retrieve the student object
					Student output = session.get(Student.class, 1L);

					// Commit the transaction
					transaction.commit();

					System.out.println(output);
				} catch (Exception e) {
					// Rollback transaction on error
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

  }

}
