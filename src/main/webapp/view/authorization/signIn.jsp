
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<div>
    <form action="/sign-in" method="post">
        <input name="email" type="email" placeholder="Email kiriting" required>
        <input name="password" type="password" placeholder="Parol kiriting" required>
        <button >Sign in</button>
        <br>
        <a href="/change-pass" >Parolni tiklash</a>
    </form>
    <a href="/view/authorization/signUp.jsp">Roxyatdan otish</a>
</div></body>
</html>
