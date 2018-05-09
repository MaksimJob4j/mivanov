<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Servlet</title>
</head>
<body>
    <% response.sendRedirect(String.format("%s/list", request.getContextPath())); %>
</body>
</html>
