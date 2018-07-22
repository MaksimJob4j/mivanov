<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update User</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        <%@include file="/WEB-INF/style.css"%>
    </style>
    <script>
        <%@include file="/WEB-INF/validate.js"%>
    </script>
</head>
<body>
<div class="container">
    <h3>Edit user:</h3>
    <form action="${pageContext.servletContext.contextPath}/update_user" method="post" onsubmit="return validate();">
        <div style='display: inline-block'>
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <td>Id</td>
                        <td>Login</td>
                        <td>Password</td>
                        <td>Role</td>
                        <td>Country</td>
                        <td>City</td>
                        <td>Rest of the address</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <input type="hidden" class="form-control" name="id" value="${user.id}">
                            <div class="padded-text">${user.id}</div>
                        </td>
                        <td><input type="text" class="form-control" name="login" id="login" value="${user.login}"></td>
                        <td><input type="text" class="form-control" name="password" id="password" value="${user.password}"></td>
                        <td>
                            <c:if test="${loginUser.role.level > 0 && loginUser.role.level < 10}">
                                <input type="hidden" class="form-control" name="role" value="USER">
                                <div class="padded-text">USER</div>
                            </c:if>
                            <c:if test="${loginUser.role.level == 10}">
                                <select class="form-control" name="role" id="role">
                                    <c:forEach items = "${roles}" var = "role">
                                        <c:if test="${user == null || user.role.name != role.name}">
                                            <option>${role.name}</option>
                                        </c:if>
                                        <c:if test="${user != null && user.role.name == role.name}">
                                            <option value="${role.name}" selected>${role.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </c:if>
                        </td>
                        <td><input type="text" class="form-control"  name="country" value="${address.country}"></td>
                        <td><input type="text" class="form-control"  name="city" value="${address.city}"></td>
                        <td><input type="text" class="form-control"  name="restAddress" value="${address.restAddress}"></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="buttons-beside">
            <div class="buttons-beside">
                <input type="submit" value="SAVE" class="btn btn-default">
            </div>
            <a href="${pageContext.servletContext.contextPath}/user_info?id=${user.id}" value="CANCEL" class="btn btn-default">CANCEL</a>
        </div>
    </form>
</div>
<div class="container" id="error">
    <div>
        <c:out value="${error}"/>
    </div>
</div>
</body>
</html>
