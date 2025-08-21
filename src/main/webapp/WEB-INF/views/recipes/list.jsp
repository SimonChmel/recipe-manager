<jsp:include page="/WEB-INF/views/_layout/header.jspf"/>
<jsp:include page="/WEB-INF/views/messages.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<h2>All Recipes</h2>
<table>
    <thead>
    <tr><th>ID</th><th>Name</th><th>Actions</th></tr>
    </thead>
    <tbody>
    <c:forEach var="recipe" items="${recipes}">
        <tr>
            <td>${recipe.id}</td>
            <td><a href="${pageContext.request.contextPath}/recipes/${recipe.id}">${recipe.name}</a></td>
            <td>
                <a href="${pageContext.request.contextPath}/recipes/${recipe.id}/edit">Edit</a>
                <form action="${pageContext.request.contextPath}/recipes/${recipe.id}/delete" method="post" style="display:inline;">
                    <s:csrfInput/>
                    <button type="submit" onclick="return confirm('Delete this recipe?')">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<a href="${pageContext.request.contextPath}/recipes/new">Add Recipe</a>

<jsp:include page="/WEB-INF/views/_layout/footer.jspf"/>
