<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Uploader</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form action="${pageContext.servletContext.contextPath}/upload" method="post"
          enctype="multipart/form-data" accept-charset="windows-1251">
        <div class="form-group">
                <label for="file">Выберете файл для загрузки</label>
                <input class="file" type="file" name="file" size="100" id="file">
        </div>
        <button type="submit" class="button-default">upload</button>
    </form>
</div>
<div class="container" id="message">
    <div>
        <c:out value="${message}"/>
    </div>
</div>
<div class="container" id="error">
    <div>
        <c:out value="${error}"/>
    </div>
</div>

</body>
</html>
