package com.learning.hibernate;

import com.learning.hibernate.model.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class JDBC {
  private static final String DRIVER = "org.postgresql.Driver";
  private static final String URL = "jdbc:postgresql://localhost:5432/database-learning";
  private static final String USERNAME = "postgres";
  private static final String PASSWORD = "test123";

  // Get connection to database
  private Connection getConnection() throws SQLException, ClassNotFoundException {
    Class.forName(DRIVER);
    return DriverManager.getConnection(URL, USERNAME, PASSWORD);
  }

  // Close resources safely
  private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
    try {
      if (rs != null) rs.close();
      if (stmt != null) stmt.close();
      if (conn != null) conn.close();
    } catch (SQLException e) {
      System.err.println("Error closing resources: " + e.getMessage());
    }
  }

  // CREATE - Insert a new student
  public boolean createStudent(Student student) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      String sql = "INSERT INTO student (id, first_name, last_name) VALUES (?, ?, ?)";
      pstmt = conn.prepareStatement(sql);
      pstmt.setLong(1, student.getId());
      pstmt.setString(2, student.getFirstName());
      pstmt.setString(3, student.getLastName());

      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    } finally {
      closeResources(conn, pstmt, null);
    }
  }

  // READ - Get student by ID
  public Student getStudentById(long id) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      String sql = "SELECT * FROM student WHERE id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setLong(1, id);

      rs = pstmt.executeQuery();

      if (rs.next()) {
        Student student = new Student();
        student.setId(rs.getLong("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        return student;
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      closeResources(conn, pstmt, rs);
    }

    return null;
  }

  // READ - Get all students
  public List<Student> getAllStudents() {
    List<Student> students = new ArrayList<>();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      stmt = conn.createStatement();
      String sql = "SELECT * FROM student";
      rs = stmt.executeQuery(sql);

      while (rs.next()) {
        Student student = new Student();
        student.setId(rs.getLong("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        students.add(student);
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      closeResources(conn, stmt, rs);
    }

    return students;
  }

  // UPDATE - Update student information
  public boolean updateStudent(Student student) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      String sql = "UPDATE student SET first_name = ?, last_name = ? WHERE id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, student.getFirstName());
      pstmt.setString(2, student.getLastName());
      pstmt.setLong(3, student.getId());

      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    } finally {
      closeResources(conn, pstmt, null);
    }
  }

  // DELETE - Delete a student
  public boolean deleteStudent(long id) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      String sql = "DELETE FROM student WHERE id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setLong(1, id);

      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    } finally {
      closeResources(conn, pstmt, null);
    }
  }
}
