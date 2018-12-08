<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        <%@include file="/WEB-INF/style.css"%>
    </style>
    <script>
        $(document).ready(
            function () {
                document.getElementsByName("login")[0].focus();
            }
        );
    </script>
</head>
<body>
<div>
    <br>
</div>
<div class="container">
    <h4>
        Create new user.
    </h4>
    <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/createUser" method="post">
        <div class="form-group">
            <label class="control-label col-sm-1" for="login">Login:</label>
            <div class="col-sm-2">
                <input class="form-control" name="login" id="login" placeholder="Enter login">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-1" for="pwd">Password:</label>
            <div class="col-sm-2">
                <input type="password" class="form-control" name="password" id="pwd" placeholder="Enter password">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-1 col-sm-10">
                <button type="submit" class="btn btn-default">Create</button>
            </div>
        </div>
    </form>
</div>
<div class="container">
    <div class="container, beside">
        <form action="${pageContext.servletContext.contextPath}/">
            <input type="submit" value="RETURN TO MAIN" class="btn btn-default">
        </form>
    </div>
</div>
<div class="container" id="error">
    <div>
        <c:out value="${error}"/>
    </div>
</div>
<div class="container" id="msg">
    <div>
        <c:out value="${msg}"/>
    </div>
</div>
</body>
</html>