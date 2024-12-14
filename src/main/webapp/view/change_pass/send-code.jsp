
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Send code</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<div>
<form action="/confirmation-code">
    <input type="email" name="email" placeholder="Enter email" required>
<%--    <input type="hidden" value="${email}">--%>
    <button>Kodni yuborish</button>
</form>
</div>
</body>
</html>
