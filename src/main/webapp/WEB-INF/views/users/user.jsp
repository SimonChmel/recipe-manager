<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/_layout/header.jspf" %>
<%@ include file="/WEB-INF/views/_layout/navbar.jsp" %>

<h1>User #${user.id}</h1>
<p><b>Username:</b> ${user.username}</p>
<p><b>Email:</b> ${user.email}</p>
<p><b>Role:</b> ${user.role}</p>

<a href="${pageContext.request.contextPath}/users">Back to Users</a>
