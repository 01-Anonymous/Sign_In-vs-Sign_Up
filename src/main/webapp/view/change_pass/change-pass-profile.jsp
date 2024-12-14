<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Parol yangilash</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<div>
    <form action="/confirmation-code">
        <input name="email" type="email" value="${email}" readonly>
        <button>Kodni yuborish</button>
    </form>
</div>
</body>
</html>
