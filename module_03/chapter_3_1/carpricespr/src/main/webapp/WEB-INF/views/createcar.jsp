<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Car</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        <%@include file="/WEB-INF/findmodels.js"%>
        <%@include file="/WEB-INF/carValidate.js"%>
    </script>
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
    <h3>New car:</h3>
    <form action="${pageContext.servletContext.contextPath}/newcar" method="post"
          enctype="multipart/form-data"
          onsubmit="return carValidate();"
    >
        <div class="row">
            <div class="col-md-3">
                Brand:
                <select class="form-control" name="brand" id="brand" onchange="findmodels()">
                    <option value="" selected disabled hidden>Choose brand</option>
                    <c:forEach items = "${carPats.brands}" var = "brand">
                        <option value="${brand.id}">${brand.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3">
                Model:
                <select class="form-control" name="model" id="model">
                    <option value="" selected disabled hidden>Choose brand first</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                Body:
                <select class="form-control" name="body">
                    <option value="" selected disabled hidden>Choose body</option>
                    <c:forEach items = "${carPats.bodies}" var = "body">
                        <option value="${body.id}">${body.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3">
                Year:
                <input type="text" class="form-control" name="year">
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                Engine:
                <select class="form-control" name="engine">
                    <option value="" selected disabled hidden>Choose engine</option>
                    <c:forEach items = "${carPats.engines}" var = "engine">
                        <option value="${engine.id}">${engine.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3">
                Volume (L):
                <input type="text" class="form-control" name="volume">
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                Transmission:
                <select class="form-control" name="transmission">
                    <option value="" selected disabled hidden>Choose transmission</option>
                    <c:forEach items = "${carPats.transmissions}" var = "transmission">
                        <option value="${transmission.id}">${transmission.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3">
                Drive:
                <select class="form-control" name="drive">
                    <option value="" selected disabled hidden>Choose drive</option>
                    <c:forEach items = "${carPats.drives}" var = "drive">
                        <option value="${drive.id}">${drive.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                Power (hp):
                <input type="text" class="form-control" name="power">
            </div>
            <div class="col-md-3">
                Mileage (km):
                <input type="text" class="form-control" name="mileage">
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                Owners number:
                <input type="text" class="form-control" name="ownersNum">
            </div>
            <div class="col-md-3">
                Vin:
                <input type="text" class="form-control" name="vin">
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                Rigt wheel:
                <input type="checkbox"  name="rightWheel" path="rightWheel"/>
            </div>
            <div class="col-md-3">
                Broken:
                <input type="checkbox"  name="broken"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                Address:
                <input type="text" class="form-control" name="address">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                Add photo:
                <input class="file" type="file" name="photofile" id="photofile">
            </div>
        </div>
        <div class="row">
            <div class="col-md-2">
                Price (RUB):
                <input type="text" class="form-control" name="price">
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <input type="submit" value="CREATE CAR" class="btn btn-default">
            </div>
        </div>
    </form>
</div>
</body>
</html>