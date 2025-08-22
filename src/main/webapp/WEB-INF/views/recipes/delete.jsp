<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirm Delete</title>
</head>
<body>
<h2>Confirm Delete</h2>

<p>You are about to delete recipe: ${recipe.name}</p>

<form action="${pageContext.request.contextPath}/recipes/delete/${recipe.id}" method="post">
    <input type="submit" value="Delete">
</form>
<a href="${pageContext.request.contextPath}/recipes">Cancel</a>

</body>
</html>