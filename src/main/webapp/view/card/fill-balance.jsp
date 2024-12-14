
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hisob toldirish</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<div style="margin: 20px 0px">
    <form action="/fill-balance?number=${number}" method="post">
        <input name="fill" type="number" min="0.1" step="0.01" placeholder="Summani kiriting">
        <button type="submit">Hisobni toldirish</button>
    </form>
</div>
</body>
</html>
