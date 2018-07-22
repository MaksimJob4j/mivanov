<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users List</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        <%@include file="/WEB-INF/style.css"%>
    </style>
</head>
<body>
<div class="container">
    <h3>Your account is:</h3>
    <div class="table-responsive" style="float: left">
        <table class="table">
            <thead>
            <tr>
                <td>Id</td>
                <td>Login</td>
                <td>Role</td>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><c:out value="${loginUser.id}" /></td>
                <td><c:out value="${loginUser.login}" /></td>
                <td><c:out value="${loginUser.role.name}" /></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="container">
    <form action="${pageContext.servletContext.contextPath}/user_info">
        <input name='id' type='hidden' value="${loginUser.id}">
        <input type='submit' value='YOUR INFO' class="btn btn-default">
    </form>
</div>
<div class="container">
    <h3>User list:</h3>
    <div class="table-responsive" style="float: left">
        <table class="table">
            <thead>
            <tr>
                <td>Id</td>
                <td>Login</td>
                <td>Role</td>
                <td>Country</td>
                <td>City</td>
                <td>Rest of the address</td>
                <c:if test="${loginUser.role.level > 0}">
                    <td></td>
                </c:if>
                <c:if test="${loginUser.role.level == 10}">
                    <td></td>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items = "${users}" var = "user">
                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.login}" /></td>
                    <td><c:out value="${user.role.name}" /></td>
                    <td><c:out value="${user.address.country}" /></td>
                    <td><c:out value="${user.address.city}" /></td>
                    <td><c:out value="${user.address.restAddress}" /></td>
                    <c:if test="${loginUser.role.level > 0}">
                        <td>
                            <c:if test="${loginUser.role.level == 10 || loginUser.role.level > user.role.level}">
                                <form action="${pageContext.servletContext.contextPath}/user_info">
                                    <input name='id' type='hidden' value="${user.id}">
                                    <input type='submit' value='INFO' class="btn btn-default">
                                </form>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${loginUser.role.level == 10}">
                        <td>
                            <form action="${pageContext.servletContext.contextPath}/delete_user" method="post" >
                                <input name='id' type='hidden' value="${user.id}">
                                <input type='submit' value='DELETE' class="btn btn-default">
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="container">
    <c:if test="${loginUser.role.level > 0}">
        <form action="${pageContext.servletContext.contextPath}/create_user" >
            <input type="submit" value="CREATE NEW USER" class="btn btn-default">
        </form>
    </c:if>
</div>
<div class="container">
    <form action="${pageContext.servletContext.contextPath}/signout">
        <input type="submit" value="SIGN OUT" class="btn btn-default">
    </form>
</div>
</body>
</html>