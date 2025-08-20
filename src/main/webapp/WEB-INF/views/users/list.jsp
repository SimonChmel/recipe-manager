<jsp:include page="/WEB-INF/views/_layout/header.jsp"/>
<jsp:include page="/WEB-INF/views/_layout/messages.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<h2>All Users</h2>
<table>
    <thead>
    <tr><th>ID</th><th>Username</th><th>Email</th><th>Role</th><th>Actions</th></tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td>
                <a href="${pageContext.request.contextPath}/users/${user.id}/edit">Edit</a>
                <form action="${pageContext.request.contextPath}/users/${user.id}/delete" method="post" style="display:inline;">
                    <s:csrfInput/>
                    <button type="submit" onclick="return confirm('Delete this user?')">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<a href="${pageContext.request.contextPath}/users/new">Add User</a>

<jsp:include page="/WEB-INF/views/_layout/footer.jsp"/>
