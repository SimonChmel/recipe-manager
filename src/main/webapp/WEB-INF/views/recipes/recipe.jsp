<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Detail</title>
</head>
<body>

<%@ include file="/WEB-INF/views/_layout/header.jspf" %>

<h2>Recipe Detail</h2>
<p></p>
<h3>Ingredients</h3>
<table border="1">
    <thead>
        <tr>
            <th>Name</th>
            <th>Quantity</th>
            <th>Unit</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="ri" items="${recipe.ingredients}">
        <tr>
            <td>${ri.ingredientName}</td>
            <td>${ri.quantity}</td>
            <td>${ri.unit}</td>
            <td>
                <form action="${pageContext.request.contextPath}/recipes/${recipe.id}/ingredients/delete/${ri.ingredientId}" method="post" style="display:inline;">
                    <s:csrfInput/>
                    <button type="submit" onclick="return confirm('Remove ingredient?')">Remove</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h3>Add Ingredient</h3>
<form action="${pageContext.request.contextPath}/recipes/${recipe.id}/ingredients/add" method="post">
    <s:csrfInput/>
    <div>
        <label for="ingredientId">Ingredient:</label>
        <select name="ingredientId" id="ingredientId">
            <c:forEach var="ing" items="${allIngredients}">
                <option value="${ing.id}">${ing.name}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label for="quantity">Quantity:</label>
        <input type="number" step="0.01" name="quantity" id="quantity" required/>
    </div>
    <div>
        <label for="unit">Unit:</label>
        <input type="text" name="unit" id="unit" required/>
    </div>
    <button type="submit">Add Ingredient</button>
</form>

<a href="${pageContext.request.contextPath}/recipes">Back to Recipes</a>

<%@ include file="/WEB-INF/views/_layout/footer.jspf" %>
</body>
</html>