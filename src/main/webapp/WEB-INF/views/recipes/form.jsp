<jsp:include page="/WEB-INF/views/_layout/header.jspf"/>
<jsp:include page="/WEB-INF/views/messages.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<h2>Create New Recipe</h2>
<form action="${pageContext.request.contextPath}/recipes" method="post">
    <s:csrfInput/>
    <div>
        <label>Name: <input type="text" name="name" value="${recipe.name}" required/></label>
    </div>
    <button type="submit">Save</button>
</form>

<a href="${pageContext.request.contextPath}/recipes">Back to list</a>

<jsp:include page="/WEB-INF/views/_layout/footer.jspf"/>
