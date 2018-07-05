<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users INFO</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        <%@include file="/WEB-INF/style.css"%>
    </style>
    <script>
        <%@include file="/WEB-INF/validate.js"%>
        <%@include file="/WEB-INF/settypes.js"%>
        $(document).ready(
            function () {
                var query = './list_type?'
                    + "&user-id=" + "${user.id}";
                settypes(query);
                document.getElementById("error").innerHTML = "${error}";
            }
        );
        function addtype() {
            var newTypeName;
            var newTypeId = $('#type-id-new').val();
            if (newTypeId === '-1') {
                newTypeName = (prompt("Insert new music type", '')).trim();
            }
            if (newTypeId !== '-1' || (newTypeName !== null && newTypeName !== '')) {
                var query = './add_type?'
                    + 'type-id-new=' + $('#type-id-new').val()
                    + "&type-name-new=" + newTypeName
                    + "&user-id=" + "${user.id}";
                settypes(query);
                document.getElementById("error").innerHTML = "${error}";
            }
        }
        function deletetype(typeid) {
            var query = './delete_type?'
                + 'type-id=' + typeid
                + "&user-id=" + "${user.id}";
            settypes(query);
            document.getElementById("error").innerHTML = "${error}";
        }
    </script>
</head>
<body>
<div class="container">
    <br test="${users != null && users.size() > 0}">
    <h3>Main Info:</h3>
</div>
<div class="container" id="main">
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
                <td><c:out value="${user.id}" /></td>
                <td><c:out value="${user.login}" /></td>
                <td><c:out value="${user.password}" /></td>
                <td><c:out value="${user.role.name}" /></td>
                <td><c:out value="${user.address.country}" /></td>
                <td><c:out value="${user.address.city}" /></td>
                <td><c:out value="${user.address.restAddress}" /></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<c:if test="${loginUser.role.level == 10 || loginUser.role.level > user.role.level || loginUser.id == user.id}">
    <div class="container" id="userbuttons">
        <div class="buttons-beside" id="alter-button">
            <a href="${pageContext.servletContext.contextPath}/update_user?id=${user.id}" value="ALTER INFO" class="btn btn-default">ALTER INFO</a>
        </div>
        <c:if test="${loginUser.role.level == 10}">
            <div class="buttons-beside">
                <form action="${pageContext.servletContext.contextPath}/delete_user" method="post" >
                    <input name='id' type='hidden' value="${user.id}">
                    <input type='submit' value='DELETE USER' class="btn btn-default">
                </form>
            </div>
        </c:if>
    </div>
</c:if>
<div class="container" id="error">
    <div id = "saveerror">
        <c:out value="${saveerror}"/>
    </div>
</div>
<div class="container" >
    <h3>Music types:</h3>
    <div class="table-responsive" >
        <table class="table" >
            <thead>
            <tr>
                <td>Misuc type</td>
                <td></td>
            </tr>
            </thead>
            <tbody id="musicTypeList">
            </tbody>
        </table>
    </div>
</div>
<div class="container">
    <a href="${pageContext.servletContext.contextPath}/" value="RETURN TO LIST" class="btn btn-default">RETURN TO LIST</a>
</div>
<div class="container" id="error">
    <div>
        <c:out value="${error}"/>
    </div>
</div>
</body>
</html>