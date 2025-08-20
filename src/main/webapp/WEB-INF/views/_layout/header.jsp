<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title><c:out value="${pageTitle != null ? pageTitle : 'Recipe Manager'}"/></title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/_layout/navbar.jsp"/>
<main>
