<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/views/_layout/header.jsp"/>
<jsp:include page="/WEB-INF/views/_layout/messages.jsp"/>

<h2>Edit Recipe</h2>
<form action="${pageContext.request.contextPath}/recipes/${recipe.id}/edit" method="post">
  <s:csrfInput/>
  <div>
    <label>Name: <input type="text" name="name" value="${recipe.name}" required/></label>
  </div>
  <button type="submit">Update</button>
</form>

<a href="${pageContext.request.contextPath}/recipes">Back to list</a>

<jsp:include page="/WEB-INF/views/_layout/footer.jsp"/>
