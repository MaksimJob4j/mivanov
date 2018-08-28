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
        <form action="${pageContext.servletContext.contextPath}/">
            <input type="submit" value="RETURN TO MAIN" class="btn btn-default">
        </form>
    </div>
</div>
<div class="container">
    <h3>${car_info.model.brand.name} ${car_info.model.name} ${car_info.year} ${car_info.price} RUB</h3>
    <h4>Posted ${car_info.dateCreated.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a X"))}</h4>
    <div class="row">
        <div class="col-md-6">
            <img src="photo?car_id=${car_info.id}" class="img-fluid" alt="Responsive image" width="500">
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            Brand: ${car_info.model.brand.name}
        </div>
        <div class="col-md-3">
            Engine: ${car_info.engine.name}
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            Model: ${car_info.model.name}
        </div>
        <div class="col-md-3">
            Drive: ${car_info.drive.name}
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            Year: ${car_info.year}
        </div>
        <div class="col-md-3">
            Wheel: ${car.rightWheel ? 'right' : 'left'}
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            Mileage: ${car_info.mileage} km
        </div>
        <div class="col-md-3">
            Condition: ${car.broken ? '' : 'not '}broken
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            Body: ${car_info.body.name}
        </div>
        <div class="col-md-3">
            Owners number: ${car_info.ownersNum}
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            Volume: ${car_info.volume} L
        </div>
        <div class="col-md-3">
            Vin: ${car_info.vin}
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            Transmission: ${car_info.transmission.name}
        </div>
        <div class="col-md-3">
            Power: ${car_info.power} hp
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            Address: ${car_info.address}
        </div>
    </div>
</div>
</body>
</html>