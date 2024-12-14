<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="../../style.css">
</head>
<body>
    <div style="max-width: 500px">
        <form action="/sign-up" method="post">
            <input name="name" type="text" placeholder="Ism kiriting" required>
            <input name="email" type="email" placeholder="Email kiriting" required>
            <input name="phone" type="text" placeholder="+998XXXXXXXXX" pattern="\+998[0-9]{9}" required>
            <input name="password" type="password" placeholder="Parol kiriting" pattern="^(?=.*\d)[A-Za-z\d]{8,}$" required>
            <button>Sign up</button>
            <a href="/sign-in">Tizimda mavjudmisiz</a>
        </form>
    </div>
</body>
</html>