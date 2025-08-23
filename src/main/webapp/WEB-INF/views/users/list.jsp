<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
</head>
<body>

<%@ include file="/WEB-INF/views/layout/header.jspf" %>

<h2>User List</h2>
<table border="1">
    <tr>
        <th>Username</th>
        <th>Action</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td><c:out value="${user.username}"/></td>
            <td>
                <a href="${pageContext.request.contextPath}/users/form?id=${user.id}">Edit</a>
                <a href="<c:out value="/users/delete/${user.id}"/>">Delete</a>
                <a href="${pageContext.request.contextPath}/users/user/${user.id}">Show</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/users/form">Add User</a>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
</body>
</html>
