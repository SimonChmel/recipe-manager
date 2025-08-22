<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirm Delete</title>
</head>
<body>
<h2>Confirm Delete</h2>

<p>You are about to delete user: ${user.username}</p>

<form action="${pageContext.request.contextPath}/users/delete/${user.id}" method="post">
    <input type="submit" value="Delete">
</form>

<a href="${pageContext.request.contextPath}/users">Cancel</a>
</body>
</html>