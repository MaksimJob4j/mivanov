<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List Users</title>
</head>
<body>
<br test="${users != null && users.size() > 0}">
<br>Your account is:<br/>
<table cellpadding="1" cellspacing="1" border="1" >
    <tr align="center" style="font-weight: bold">
        <td>Id</td>
        <td>Login</td>
        <td>Role</td>
        <td>Name</td>
        <td>Email</td>
        <td>CreateDate</td>
        <td></td>
    </tr>
    <tr>
        <td><c:out value="${loginUser.id}" /></td>
        <td><c:out value="${loginUser.login}" /></td>
        <td><c:out value="${loginUser.role}" /></td>
        <td><c:out value="${loginUser.name}" /></td>
        <td><c:out value="${loginUser.email}" /></td>
        <td><c:out value="${loginUser.createDate}" /></td>
        <td>
            <form action="${pageContext.servletContext.contextPath}/edit">
                <input name='id' type='hidden' value="${loginUser.id}">
                <input type='submit' value='EDIT'>
            </form>
        </td>
    </tr>
</table>
<br>User list:<br/>
<table cellpadding="1" cellspacing="1" border="1" >
    <tr align="center" style="font-weight: bold">
        <td>Id</td>
        <td>Login</td>
        <td>Role</td>
        <td>Name</td>
        <td>Email</td>
        <td>CreateDate</td>
        <c:if test="${loginUser.role.equals(\"admin\")}">
            <td></td>
            <td></td>
        </c:if>
    </tr>
    <c:forEach items = "${users}" var = "user">
        <tr>
            <td><c:out value="${user.id}" /></td>
            <td><c:out value="${user.login}" /></td>
            <td><c:out value="${user.role}" /></td>
            <td><c:out value="${user.name}" /></td>
            <td><c:out value="${user.email}" /></td>
            <td><c:out value="${user.createDate}" /></td>
            <c:if test="${loginUser.role.equals(\"admin\") }">
                <td>
                    <form action="${pageContext.servletContext.contextPath}/edit">
                        <input name='id' type='hidden' value="${user.id}">
                        <input type='submit' value='EDIT'>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/delete" method="post" >
                        <input name='id' type='hidden' value="${user.id}">
                        <input type='submit' value='DELETE'>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
<br>
<c:if test="${loginUser.role.equals(\"admin\")}">
    <form action="${pageContext.servletContext.contextPath}/create" >
        <input type="submit" value="CREATE NEW USER">
    </form>
</c:if>
<br>
<form action="${pageContext.servletContext.contextPath}/signout">
    <input type="submit" value="SIGN OUT">
</form>
</body>
</html>
