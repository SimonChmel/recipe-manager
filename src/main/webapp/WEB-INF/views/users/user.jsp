<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Detail</title>
</head>
<body>

<%@ include file="/WEB-INF/views/_layout/header.jspf" %>

<h2>User Detail</h2>
<table border="1">
    <thead>
        <th>ID</th>
        <th>Username</th>
        <th>Email</th>
        <th>Role</th>
    </thead>
    <tbody>
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
        <tr>
    <tbody>
</table>

<a href="${pageContext.request.contextPath}/users">Back to Users</a>
<%@ include file="/WEB-INF/views/_layout/footer.jspf" %>
</body>
</html>