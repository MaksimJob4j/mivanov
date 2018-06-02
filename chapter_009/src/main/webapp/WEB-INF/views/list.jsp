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
    <br test="${users != null && users.size() > 0}">
    <h3>Your account is:</h3>
    <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <td>Id</td>
                <td>Login</td>
                <td>Role</td>
                <td>Name</td>
                <td>Email</td>
                <td>Country</td>
                <td>City</td>
                <td>CreateDate</td>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><c:out value="${loginUser.id}" /></td>
                <td><c:out value="${loginUser.login}" /></td>
                <td><c:out value="${loginUser.role}" /></td>
                <td><c:out value="${loginUser.name}" /></td>
                <td><c:out value="${loginUser.email}" /></td>
                <td><c:out value="${loginUser.country}" /></td>
                <td><c:out value="${loginUser.city}" /></td>
                <td><c:out value="${loginUser.createDate}" /></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="container">
    <form action="${pageContext.servletContext.contextPath}/edit">
        <input name='id' type='hidden' value="${loginUser.id}">
        <input type='submit' value='EDIT YOUR ACCOUNT' class="btn btn-default">
    </form>
</div>
<div class="container">
    <h3>User list:</h3>
    <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <td>Id</td>
                <td>Login</td>
                <td>Role</td>
                <td>Name</td>
                <td>Email</td>
                <td>Country</td>
                <td>City</td>
                <td>CreateDate</td>
                <c:if test="${loginUser.role.equals(\"admin\")}">
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items = "${users}" var = "user">
                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.login}" /></td>
                    <td><c:out value="${user.role}" /></td>
                    <td><c:out value="${user.name}" /></td>
                    <td><c:out value="${user.email}" /></td>
                    <td><c:out value="${user.country}" /></td>
                    <td><c:out value="${user.city}" /></td>
                    <td><c:out value="${user.createDate}" /></td>
                    <c:if test="${loginUser.role.equals(\"admin\") }">
                        <td>
                            <form action="${pageContext.servletContext.contextPath}/edit">
                                <input name='id' type='hidden' value="${user.id}">
                                <input type='submit' value='EDIT' class="btn btn-default">
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.servletContext.contextPath}/delete" method="post" >
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
    <c:if test="${loginUser.role.equals(\"admin\")}">
        <form action="${pageContext.servletContext.contextPath}/create" >
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