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

        /*#error {*/
            /*background-color: red;*/
            /*color: yellow;*/
            /*font-weight: bold;*/
        /*}*/
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
    <div class="form-inline">
        <form action="${pageContext.servletContext.contextPath}/signin" method="post">
            <table>
                <tr>
                    <div class="form-group">
                        <td>
                            <label >Login:  </label>
                        </td>
                        <td>
                            <input name="login" class="form-control">
                        </td>
                    </div>
                </tr>
                <tr>
                    <div class="form-group">
                        <td>
                            <label>Password:</label>
                        </td>
                        <td>
                            <input type="password" name="password" class="form-control">
                        </td>
                    </div>
                </tr>
            </table>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </div>
</div>
<div class="container" id="error">
    <div>
        <c:out value="${error}"/>
    </div>
</div>
</body>
</html>