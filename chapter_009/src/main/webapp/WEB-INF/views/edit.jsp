<%@ page import="ru.job4j.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<%User user = (User) request.getAttribute("user");%>
<form action="<%=request.getContextPath()%>/edit2" method="post" >
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
            <%=user.getId()%>
            <input name="id" type="hidden" value="<%=user.getId()%>">
        </td>
        <td><input name="name" value="<%=user.getName()%>"></td>
        <td><input name="login" value="<%=user.getLogin()%>"></td>
        <td><input name="email" value="<%=user.getEmail()%>"></td>
        <td><%=user.getCreateDate()%></td>
    </tr>
    </table>
    <input type="submit" value="ACCEPT">
</form>

<form action="<%=request.getContextPath()%>/">
    <input type="submit" value="CANCEL">
</form>

</body>
</html>
