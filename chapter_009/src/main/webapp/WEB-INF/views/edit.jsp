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
            <td>Login</td>
            <td>Password</td>
            <td>Role</td>
            <td>Name</td>
            <td>Email</td>
            <td>CreateDate</td>
        </tr>
        <tr>
            <td>
                <c:out value="${user.id}" />
                <input name="id" type="hidden" value="<c:out value="${user.id}"/>" >
            </td>
            <td><input name="login" value="<c:out value="${user.login}"/>" ></td>
            <td><input name="password" value="<c:out value="${user.password}"/>" ></td>
            <td>
                <c:if test="${roles != null}">
                    <select name="role">
                        <c:forEach items = "${roles}" var = "role">
                            <option
                                    <c:if test="${role.name.equals(user.role)}">selected</c:if> value="${role.name}">
                                <c:out value="${role.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </c:if>
                <c:if test="${roles == null}">
                    <c:out value="${user.role}"/>
                </c:if>
            </td>
            <td><input name="name" value="<c:out value="${user.name}"/>" ></td>
            <td><input name="email" value="<c:out value="${user.email}"/>" ></td>
            <td><c:out value="${user.createDate}" /></td>
        </tr>
    </table>
    <input type="submit" value="ACCEPT">
</form>

<form action="${pageContext.servletContext.contextPath}/">
    <input type="submit" value="CANCEL">
</form>

<div style="background-color: red; color: yellow; font-weight: bold">
    <c:out value="${error}"/>
</div>

</body>
</html>
