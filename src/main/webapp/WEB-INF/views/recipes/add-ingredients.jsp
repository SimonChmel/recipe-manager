<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head><title>Add Ingredient</title></head>
<body>
<h1>Add Ingredient</h1>
<form:form modelAttribute="ingredient" method="post">
    Ingredient ID: <form:input path="ingredientId"/><br/>
    Quantity: <form:input path="quantity"/><br/>
    Unit: <form:input path="unit"/><br/>
    <button type="submit">Add</button>
</form:form>
</body>
</html>
