<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/signin" method="post">
    <br>Login: <input name="login"><br/>
    <br>Password: <input type="password" name="password"><br/>
    <input type="submit">
</form>

<div style="background-color: red; color: yellow; font-weight: bold">
    <c:out value="${error}"/>
</div>

</body>
</html>
