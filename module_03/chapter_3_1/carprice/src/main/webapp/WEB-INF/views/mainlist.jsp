<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Car List</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        <%@include file="/WEB-INF/style.css"%>
    </style>
    <script>
        <%@include file="/WEB-INF/changeSold.js"%>
        <%@include file="/WEB-INF/applyCarFilter.js"%>
        $(document).ready(
            function () {
                applyCarFilter();
            }
        );
    </script>
</head>
<body>
<br/>
<div class="container">
    <h4>Login: ${loginUser.login}</h4>
    <input value="${loginUser.id}" id="login_id" hidden/>
    <div class="container, beside">
        <form action="${pageContext.servletContext.contextPath}/signout">
            <input type="submit" value="SIGN OUT" class="btn btn-default">
        </form>
    </div>
    <div class="container, beside">
        <form action="${pageContext.servletContext.contextPath}/users_cars">
            <input type="submit" value="USER'S CARS" class="btn btn-default">
        </form>
    </div>
    <div class="container, beside">
        <form action="${pageContext.servletContext.contextPath}/newcar">
            <input type="submit" value="CREATE NEW CAR" class="btn btn-default">
        </form>
    </div>
</div>
<div class="container">
        <form class="form-inline" onchange="applyCarFilter()" action="">
            <select class="form-control" id="brand_filter" onchange="">
                <option value="" selected>All brands</option>
                <c:forEach items = "${brands}" var = "brand">
                    <option value="${brand.id}" >${brand.name}</option>
                </c:forEach>
            </select>
            <div class="checkbox">
                <label><input type="checkbox" id="date_filter"> Last day</label>
            </div>
            <div class="checkbox">
                <label><input type="checkbox" id="photo_filter"> Photo</label>
            </div>
        </form>
</div>
<div class="container">
    <h3>Cars for sale:</h3>
    <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <td>Id</td>
                <td>Car</td>
                <td>Price</td>
                <td>Created</td>
                <td>Sold</td>
                <td>Owner</td>
                <td></td>
            </tr>
            </thead>
            <tbody id="tbody_cars">
            </tbody>
        </table>
    </div>
</div>
</body>
</html>