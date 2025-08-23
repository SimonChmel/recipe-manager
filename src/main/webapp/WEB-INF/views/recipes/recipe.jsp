<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ingredient Detail</title>
</head>
<body>

<%@ include file="/WEB-INF/views/layout/header.jspf" %>

<h2>Recipe Detail</h2>
<p>${recipe.name}</p>
<p>${recipe.description}</p>
<h3>Ingredients</h3>
<table border="1">
    <thead>
        <tr>
            <th>Ingredient</th>
            <th>Quantity</th>
            <th>Unit</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="ingredient" items="${ingredients}">
            <tr>
                <td><c:out value="${ingredient.ingredientName}"/></td>
                <td><c:out value="${ingredient.quantity}"/></td>
                <td><c:out value="${ingredient.unit}"/></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/recipes">Back to Recipes</a>

<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
</body>
</html>