<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/_layout/header.jspf"/>

<h2>Oops! Something went wrong.</h2>

<p>
    We couldn't process your request.
    <c:if test="${not empty message}">
        <br/>Details: ${message}
    </c:if>
</p>

<p><a href="${pageContext.request.contextPath}/">Return to Home</a></p>

<jsp:include page="/WEB-INF/views/_layout/footer.jspf"/>
