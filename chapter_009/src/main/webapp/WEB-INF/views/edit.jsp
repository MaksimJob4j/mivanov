<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/edit" method="post" >
    <table cellpadding="1" cellspacing="1" border="1" >
        <tr align="center" style="font-weight: bold">
            <td>Id</td>
            <td>Name</td>
            <td>Login</td>
            <td>Email</td>
            <td>CreateDate</td>
        </tr>
        <tr>
            <td>
                <c:out value="${user.id}" />
                <input name="id" type="hidden" value="<c:out value="${user.id}"/>" >
            </td>
            <td><input name="name" value="<c:out value="${user.name}"/>" ></td>
            <td><input name="login" value="<c:out value="${user.login}"/>" ></td>
            <td><input name="email" value="<c:out value="${user.email}"/>" ></td>
            <td><c:out value="${user.createDate}" /></td>
        </tr>
    </table>
    <input type="submit" value="ACCEPT">
</form>

<form action="${pageContext.servletContext.contextPath}/">
    <input type="submit" value="CANCEL">
</form>

</body>
</html>
