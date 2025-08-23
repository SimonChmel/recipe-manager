<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ingredient Detail</title>
</head>
<body>

<%@ include file="/WEB-INF/views/layout/header.jspf" %>

<h2>Ingredient Detail</h2>
<p>${ingredient.name}</p>
<p>${recipe.description}</p>

</body>
</html>
