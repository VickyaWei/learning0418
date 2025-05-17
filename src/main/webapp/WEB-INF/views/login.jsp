<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="/css/styles.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="login-container">
    <h2>Sign In</h2>

    <!-- Display error message if login fails -->
    <c:if test="${param.error != null}">
        <div class="alert alert-danger">
            Invalid username or password.
        </div>
    </c:if>

    <!-- Display logout message -->
    <c:if test="${param.logout != null}">
        <div class="alert alert-success">
            You have been logged out successfully.
        </div>
    </c:if>

    <!-- Form login -->
    <div class="login-form">
        <h3>Login with credentials</h3>
        <form action="/login" method="post">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">Login</button>
            </div>
        </form>
    </div>

    <!-- OAuth2 login options -->
    <div class="oauth-login">
        <h3>Or sign in with</h3>
        <div class="oauth-buttons">
            <a href="/oauth2/authorization/google" class="btn btn-google">Google</a>
            <!-- Add more OAuth providers as needed -->
        </div>
    </div>

    <div class="register-link">
        <p>Don't have an account? <a href="/register">Register here</a></p>
    </div>
</div>

<script src="/js/login.js"></script>
</body>
</html>