<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
    </script>
</head>
<body>
<br/>
<div class="container">
    <h4>Login: ${loginUser.login}</h4>
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
            <tbody>
            <c:forEach items = "${cars}" var = "car">
                <tr
                        <c:if test="${car.owner == loginUser}">
                            class="users_car"
                        </c:if>
                >
                    <td class="id" name='id' volume="${car.id}">${car.id}</td>
                    <td>${car.model.brand.name} ${car.model.name} ${car.engine.name}
                            ${car.volume} ${car.year} ${car.color.name} ${car.broken ? 'broken ' : ''}</td>
                    <td>${car.price} RUB</td>
                    <td>${car.dateCreated.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a X"))}</td>
                    <td>
                        <input class="check-box" type="checkbox" name="sold"
                                <c:if test="${car.sold}">
                                    checked
                                </c:if>
                                <c:if test="${car.owner != loginUser}">
                                    disabled
                                </c:if>
                               onclick="changeSold(${car.id})"/>
                    </td>
                    <td>
                            ${car.owner.login}
                    </td>
                    <td>
                        <form action="${pageContext.servletContext.contextPath}/car">
                            <input name='id' type='hidden' value="${car.id}">
                            <input type='submit' value='INFO' class="btn btn-default">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>