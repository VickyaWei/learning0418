<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Customer Portal</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        padding: 20px 0;
      }
      .register-container {
        background-color: white;
        border-radius: 5px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        padding: 30px;
        width: 400px;
      }
      h2 {
        text-align: center;
        color: #333;
        margin-bottom: 25px;
      }
      .form-group {
        margin-bottom: 20px;
      }
      label {
        display: block;
        margin-bottom: 5px;
        color: #555;
        font-weight: bold;
      }
      input[type="text"],
      input[type="email"],
      input[type="password"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
      }
      .btn {
        background-color: #4285f4;
        color: white;
        border: none;
        padding: 12px;
        border-radius: 4px;
        width: 100%;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s;
      }
      .btn:hover {
        background-color: #3367d6;
      }
      .error-message {
        color: #d32f2f;
        background-color: #fde8e8;
        padding: 10px;
        border-radius: 4px;
        margin-bottom: 20px;
        text-align: center;
      }
      .login-link {
        text-align: center;
        margin-top: 20px;
      }
      .login-link a {
        color: #4285f4;
        text-decoration: none;
      }
      .login-link a:hover {
        text-decoration: underline;
      }
      .oauth-buttons {
        margin-top: 20px;
        text-align: center;
      }
      .oauth-btn {
        display: inline-block;
        width: 100%;
        padding: 10px;
        margin-bottom: 10px;
        border-radius: 4px;
        text-decoration: none;
        color: white;
        font-weight: bold;
        text-align: center;
      }
      .google-btn {
        background-color: #DB4437;
      }
      .or-divider {
        text-align: center;
        margin: 20px 0;
        position: relative;
      }
      .or-divider:before,
      .or-divider:after {
        content: "";
        position: absolute;
        top: 50%;
        width: 45%;
        height: 1px;
        background-color: #ddd;
      }
      .or-divider:before {
        left: 0;
      }
      .or-divider:after {
        right: 0;
      }
    </style>
</head>
<body>
<div class="register-container">
    <h2>Create Customer Account</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/api/customers" method="post" id="registerForm">
        <div class="form-group">
            <label for="firstName">First Name</label>
            <input type="text" id="firstName" name="firstName" required>
        </div>

        <div class="form-group">
            <label for="lastName">Last Name</label>
            <input type="text" id="lastName" name="lastName" required>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>

        <!-- Hidden field to set default role -->
        <input type="hidden" name="roles" value="ROLE_USER">

        <button type="submit" class="btn">Register</button>
    </form>

    <div class="or-divider">OR</div>

    <div class="oauth-buttons">
        <a href="${pageContext.request.contextPath}/oauth2/authorization/google" class="oauth-btn google-btn">Register with Google</a>
    </div>

    <div class="login-link">
        <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a></p>
    </div>
</div>

<script>
  // Client-side validation for the registration form
  document.getElementById('registerForm').addEventListener('submit', function(event) {
    var password = document.getElementById('password').value;
    var confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
      event.preventDefault();

      // Create an error message
      var errorDiv = document.createElement('div');
      errorDiv.className = 'error-message';
      errorDiv.textContent = 'Passwords do not match';

      // Find where to insert the error message
      var form = document.getElementById('registerForm');
      var submitButton = form.querySelector('button[type="submit"]');

      // Remove any existing error message about passwords
      var existingErrors = form.querySelectorAll('.error-message');
      existingErrors.forEach(function(error) {
        if (error.textContent === 'Passwords do not match') {
          error.remove();
        }
      });

      // Insert the error before the submit button
      form.insertBefore(errorDiv, submitButton);

      // Scroll to the error message
      errorDiv.scrollIntoView({ behavior: 'smooth' });
    }
  });
</script>
</body>
</html>