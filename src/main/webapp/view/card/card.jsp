
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Card</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<div>
    <form action="/create-card" method="post">
        <div>
            <input name="password" type="password" maxlength="4" pattern="\d{4}"
                    placeholder="Kartaga 4 xonali parol kiriting" required>
        </div>
        <div>
            <select name="type" id="type">
                <c:forEach items="${types}" var="t">
                    <option name="type" value="${t.name()}">
                        <c:out value="${t.name()}"></c:out>
                    </option>
                </c:forEach>
            </select>
        </div>
        <button type="submit">Karta yaratish</button>
    </form>
</div>
</body>
</html>
