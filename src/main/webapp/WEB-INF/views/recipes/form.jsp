<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Recipe Form</title>
</head>
<body>

<%@ include file="/WEB-INF/views/_layout/header.jspf" %>

<h2>
    <c:choose>
    <c:when test="${recipe.id != null}">Edit Recipe</c:when>
    <c:otherwise>Create Recipe</c:otherwise>
    </c:choose>
</h2>

<form:form method="post" modelAttribute="recipe" action="${pageContext.request.contextPath}/recipes/save">
    <form:hidden path="id"/>
    <table border="1">
        <tr>
            <td>Name:</td>
            <td><form:input path="name"/></td>
            <td><form:errors path="name" cssStyle="color: red"/></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><form:input path="description"/></td>
            <td><form:errors path="description" cssStyle="color: red"/></td>
        </tr>
    </table>
<button type="submit">Edit</button>
</form:form>

<a href="${pageContext.request.contextPath}/recipes">Back to list</a>
<%@ include file="/WEB-INF/views/_layout/footer.jspf" %>
</body>
</html>