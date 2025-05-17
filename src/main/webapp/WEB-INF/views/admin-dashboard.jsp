<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Admin Dashboard</title>
</head>
<body>
  <h1>Admin Dashboard</h1>
  <p>Welcome, ${username}</p>
  <p>Your roles: ${roles}</p>

  <h2>Admin Tools</h2>
  <ul>
    <li><a href="/api/customers">View All Customers (CRUD)</a></li>
    <!-- Add more admin-only tools here -->
  </ul>

  <a href="/logout">Logout</a>
</body>
</html>
