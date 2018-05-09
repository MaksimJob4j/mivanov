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
            <td>Name</td>
            <td>Login</td>
            <td>Email</td>
        </tr>
        <tr>
            <td><input name="name" value=""></td>
            <td><input name="login" value=""></td>
            <td><input name="email" value=""></td>
        </tr>
    </table>
    <input type="submit" value="CREATE">
</form>

<form action="${pageContext.servletContext.contextPath}/">
    <input type="submit" value="CANCEL">
</form>

</body>
</html>