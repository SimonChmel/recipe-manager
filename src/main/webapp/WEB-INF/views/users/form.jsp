<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>User Form</title>
</head>
<body>

<%@ include file="/WEB-INF/views/_layout/header.jspf" %>

<h2>
    <c:choose>
    <c:when test="${user.id != null}">Edit User</c:when>
    <c:otherwise>Create User</c:otherwise>
    </c:choose>
</h2>

<form:form method="post" modelAttribute="user" action="${pageContext.request.contextPath}/users/save">
    <form:hidden path="id"/>
    <table border="1">
        <tr>
            <td>Username:</td>
            <td><form:input path="username"/></td>
            <td><form:errors path="username" cssStyle="color: red"/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><form:input path="email"/></td>
            <td><form:errors path="email" cssStyle="color: red"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:password path="password"/></td>
            <td><form:errors path="password" cssStyle="color: red"/></td>
        </tr>
        <tr>
            <td>Role:</td>
            <td>
                <form:select path="role">
                    <form:option value="" label="--Select Role--" />
                    <form:options items="${roles}" />
                </form:select>
            </td>
        </tr>
    </table>
    <button type="submit">
        <c:choose>
            <c:when test='${user.id != null}'>Update</c:when>
            <c:otherwise>Create</c:otherwise>
        </c:choose>
    </button>
</form:form>
<%@ include file="/WEB-INF/views/_layout/footer.jspf" %>
</body>
</html>
