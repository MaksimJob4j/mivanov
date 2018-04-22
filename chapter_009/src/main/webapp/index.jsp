<%@ page import="ru.job4j.User" %>
<%@ page import="ru.job4j.UserStore" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UserServletJSP</title>
</head>
<body>

<% List<User> users =  UserStore.INSTANCE.getUsers();%>
<% if (users.size() > 0) {%>
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
    <% for (User user: users) {%>
    <tr>
        <td><%=user.getId()%></td>
        <td><%=user.getName()%></td>
        <td><%=user.getLogin()%></td>
        <td><%=user.getEmail()%></td>
        <td><%=user.getCreateDate()%></td>
        <td>
            <form action="<%=request.getContextPath()%>/edit.jsp">
                <input name='id' type='hidden' value="<%=user.getId()%>">
                <input type='submit' value='EDIT'>
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/deletejsp" method="post" >
                <input name='id' type='hidden' value="<%=user.getId()%>">
                <input type='submit' value='DELETE'>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<% } else {%>
<p>There's nothing here!</p>
<% } %>

<form action="<%=request.getContextPath()%>/create.jsp" >
    <input type="submit" value="CREATE">
</form>

</body>
</html>
