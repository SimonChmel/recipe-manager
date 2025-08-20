<jsp:include page="/WEB-INF/views/_layout/header.jsp"/>
<jsp:include page="/WEB-INF/views/_layout/messages.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<h2>Create New User</h2>
<form action="${pageContext.request.contextPath}/users" method="post">
    <s:csrfInput/>
    <div>
        <label>Username: <input type="text" name="username" value="${user.username}" required/></label>
    </div>
    <div>
        <label>Email: <input type="email" name="email" value="${user.email}" required/></label>
    </div>
    <div>
        <label>Password: <input type="password" name="password" required/></label>
    </div>
    <div>
        <label>Role:
            <select name="role">
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
            </select>
        </label>
    </div>
    <button type="submit">Save</button>
</form>

<a href="${pageContext.request.contextPath}/users">Back to list</a>

<jsp:include page="/WEB-INF/views/_layout/footer.jsp"/>
