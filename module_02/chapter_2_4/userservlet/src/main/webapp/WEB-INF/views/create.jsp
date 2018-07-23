<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create User</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        <%@include file="/WEB-INF/style.css"%>
    </style>
    <script>
        <%@include file="/WEB-INF/findcities.js"%>
        <%@include file="/WEB-INF/validate.js"%>
        $(document).ready(
            function () {
                if (${user != null && user.country != null}) {
                    findcities()
                }
            }
        );
    </script>
</head>
<body>
<div class="container">
    <h3>Creating user:</h3>
    <form action="${pageContext.servletContext.contextPath}/create" method="post" onsubmit="return validate();">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <td>Login</td>
                    <td>Password</td>
                    <td>Role</td>
                    <td>Name</td>
                    <td>Email</td>
                    <td>Country</td>
                    <td>City</td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><input type="text" class="form-control" name="login" value="${user.login}"></td>
                    <td><input type="text" class="form-control" name="password" value="${user.password}"></td>
                    <td>
                        <select class="form-control" name="role">
                            <c:if test="${user == null }">
                                <option value="" selected disabled hidden>Choose role</option>
                            </c:if>
                            <c:forEach items = "${roles}" var = "role">
                                <c:if test="${user == null || user.role != role.name}">
                                    <option>${role.name}</option>
                                </c:if>
                                <c:if test="${user != null && user.role == role.name}">
                                    <option value="${user.role}" selected>${role.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="text" class="form-control" name="name" value="${user.name}"></td>
                    <td><input type="text" class="form-control" name="email" value="${user.email}"></td>
                    <td>
                        <select class="form-control" name="country" onchange="findcities()">
                            <%--<c:if test="${user == null || user.country == null}">--%>
                                <option value="" selected disabled hidden>Choose country</option>
                            <%--</c:if>--%>
                                <c:forEach items = "${countries}" var = "count">
                                    <c:if test="${count != \"Other\"}">
                                        <c:if test="${user.country != count}">
                                            <option>${count}</option>
                                        </c:if>
                                        <c:if test="${user.country == count}">
                                            <option value="${count}" selected>${count}</option>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                                <option <c:if test="${user.country == \"Other\"}">value="Other" selected</c:if>>Other</option>
                        </select>
                    </td>
                    <td id="cityselect">
                        <select class="form-control" name="city">
                            <option value="" selected disabled hidden>Choose country first</option>
                        </select>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <input type="submit" value="CREATE" CLASS="btn btn-default">
    </form>
</div>
<div class="container">
    <form action="${pageContext.servletContext.contextPath}/">
        <input type="submit" value="CANCEL" CLASS="btn btn-default">
    </form>
</div>
<div class="container" id="error">
    <div>
        <c:out value="${error}"/>
    </div>
</div>
</body>
</html>