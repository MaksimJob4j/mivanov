<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create User</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/create" method="post" >
    <table cellpadding="1" cellspacing="1" border="1" >
        <tr align="center" style="font-weight: bold">
            <td>Login</td>
            <td>Password</td>
            <td>Role</td>
            <td>Name</td>
            <td>Email</td>
        </tr>
        <tr>
            <td><input name="login" value=""></td>
            <td><input name="password" value=""></td>
            <td>
                <select name="role">
                    <c:forEach items = "${roles}" var = "role">
                        <option>${role.name}</option>
                    </c:forEach>
                </select>
            </td>
            <td><input name="name" value=""></td>
            <td><input name="email" value=""></td>
        </tr>
    </table>
    <input type="submit" value="CREATE">
</form>

<form action="${pageContext.servletContext.contextPath}/">
    <input type="submit" value="CANCEL">
</form>

<div style="background-color: red; color: yellow; font-weight: bold">
    <c:out value="${error}"/>
</div>

</body>
</html>