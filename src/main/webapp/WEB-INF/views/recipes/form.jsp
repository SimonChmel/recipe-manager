<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Recipe Form</title>
</head>
<body>

<%@ include file="/WEB-INF/views/layout/header.jspf" %>

<h2>
    <c:choose>
    <c:when test="${recipe.id != null}">Edit Recipe</c:when>
    <c:otherwise>Create Recipe</c:otherwise>
    </c:choose>
</h2>

<form:form method="post" modelAttribute="recipe" action="${pageContext.request.contextPath}/recipes/save">
    <form:hidden path="id"/>
    <table border="1">
        <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <form:textarea path="name" rows="1"/>
                    <form:errors path="name" cssStyle="color: red"/>
                </td>
                <td>
                    <form:textarea path="description" rows="1" cols="30"
                    oninput="this.style.height='auto'; this.style.height=this.scrollHeight+'px'"/>
                    <form:errors path="description" cssStyle="color: red"/>
                </td>
            </tr>
        </tbody>
    </table>
</form:form>

<h2>
    <c:choose>
    <c:when test="${ingredient.ingredientId != null}">Edit Ingredients</c:when>
    <c:otherwise>Add Ingredients</c:otherwise>
    </c:choose>
</h2>

<form:form method="post" modelAttribute="ingredient" action="${pageContext.request.contextPath}/ingredient">

    <table border="1">
        <thead>
            <tr>
                <th>Ingredient</th>
                <th>Quantity</th>
                <th>Unit</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><form:textarea path="ingredientName"/></td>
            </tr>
        </tbody>
    </table>

<button type="submit">
    <c:choose>
        <c:when test='${recipe.id != null}'>Edit</c:when>
        <c:otherwise>Create</c:otherwise>
    </c:choose>
</button>
</form:form>
<a href="${pageContext.request.contextPath}/recipes">Back to list</a>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
</body>
</html>