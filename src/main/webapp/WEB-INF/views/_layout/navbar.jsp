<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<nav>
  <a href="${pageContext.request.contextPath}/">Home</a>
  <a href="${pageContext.request.contextPath}/users">Users</a>
  <a href="${pageContext.request.contextPath}/recipes">Recipes</a>
  <c:if test="${pageContext.request.userPrincipal != null}">
    <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
      <s:csrfInput/>
      <button type="submit">Logout (${pageContext.request.userPrincipal.name})</button>
    </form>
  </c:if>
</nav>
<hr/>
