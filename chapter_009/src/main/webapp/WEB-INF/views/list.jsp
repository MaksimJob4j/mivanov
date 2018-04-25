<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>

<c:if test="${users.size() > 0}">
    <table cellpadding="1" cellspacing="1" border="1" >
        <tr align="center" style="font-weight: bold">
            <td>Id</td>
            <td>Name</td>
            <td>Login</td>
            <td>Email</td>
            <td>CreateDate</td>
            <td></td>
            <td></td>
        </tr>
        <c:forEach items = "${users}" var = "user">
            <tr>
                <td><c:out value="${user.id}" /></td>
                <td><c:out value="${user.name}" /></td>
                <td><c:out value="${user.login}" /></td>
                <td><c:out value="${user.email}" /></td>
                <td><c:out value="${user.createDate}" /></td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/edit2">
                        <input name='id' type='hidden' value="${user.id}">
                        <input type='submit' value='EDIT'>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/delete2" method="post" >
                        <input name='id' type='hidden' value="${user.id}">
                        <input type='submit' value='DELETE'>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${users == null || users.size() == 0}">
    <p>There's nothing here!</p>
</c:if>

<form action="${pageContext.servletContext.contextPath}/create2">
    <input type="submit" value="CREATE">
</form>

</body>
</html>
