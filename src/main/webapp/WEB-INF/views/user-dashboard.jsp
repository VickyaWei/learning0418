<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Dashboard</title>
</head>
<body>
<h1>User Dashboard</h1>
<p>Welcome, ${username}</p>
<p>Your roles: ${roles}</p>

<h2>Your Info</h2>
<p>Access your accounts or update your profile.</p>

<a href="/logout">Logout</a>
</body>
</html>
