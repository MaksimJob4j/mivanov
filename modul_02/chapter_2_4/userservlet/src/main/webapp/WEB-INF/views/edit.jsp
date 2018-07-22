<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
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
                if (${user.country != null}) {
                    findcities()
                }
            }
        );
    </script>
</head>
<body>
<div class="container">
    <h3>Edit user:</h3>
    <form action="${pageContext.servletContext.contextPath}/edit" method="post" onsubmit="return validate();">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <td>Id</td>
                    <td>Login</td>
                    <td>Password</td>
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
                    <td>
                        <c:out value="${user.id}" />
                        <input class="form-control"  name="id" type="hidden" value="${user.id}" >
                    </td>
                    <td><input type="text" class="form-control"  name="login" value="${user.login}"></td>
                    <td><input type="text" class="form-control"  name="password" value="${user.password}"></td>
                    <td>
                        <c:if test="${roles != null}">
                            <select class="form-control" name="role">
                                <c:forEach items = "${roles}" var = "rol">
                                    <c:if test="${user.role != rol.name}">
                                        <option>${rol.name}</option>
                                    </c:if>
                                    <c:if test="${user.role == rol.name}">
                                        <option value="${rol.name}" selected>${rol.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </c:if>
                        <c:if test="${roles == null}">
                            <c:out value="${user.role}"/>
                        </c:if>
                    </td>
                    <td><input type="text" class="form-control"  name="name" value="${user.name}"></td>
                    <td><input type="text" class="form-control"  name="email" value="${user.email}"></td>
                    <td>
                        <select class="form-control" name="country" onchange="findcities()">
                            <%--<c:if test="${user.country == null}">--%>
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
                    <td><c:out value="${user.createDate}" /></td>
                </tr>
                </tbody>
            </table>
        </div>
        <input type="submit" value="ACCEPT" class="btn btn-default">
    </form>
</div>
<div class="container">
    <form action="${pageContext.servletContext.contextPath}/">
        <input type="submit" value="CANCEL" class="btn btn-default">
    </form>
</div>
<div class="container" id="error">
    <div>
        <c:out value="${error}"/>
    </div>
</div>
</body>
</html>
