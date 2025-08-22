<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Recipe List</title>
</head>
<body>

<%@ include file="/WEB-INF/views/_layout/header.jspf" %>

<h2>Recipe List</h2>
<table border="1">
    <tr>
        <th>Name</th>
    </tr>
    <c:forEach var="recipe" items="${recipes}">
        <tr>
            <td><c:out value="${recipe.name}"/></td>
            <td>
                <a href="${pageContext.request.contextPath}/recipes/form?id=${recipe.id}">Edit</a>
                <a href="${pageContext.request.contextPath}/recipes/delete/${recipe.id}">Delete</a>
                <a href="${pageContext.request.contextPath}/recipes/recipe/${recipe.id}">Show</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/recipes/form">Add Recipe</a>

<%@ include file="/WEB-INF/views/_layout/footer.jspf" %>
</body>
</html>
