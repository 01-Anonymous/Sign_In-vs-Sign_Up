<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="/style.css">
    <style>
        body {
            font-family: Arial;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 10px;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 5px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .button-container {
            margin-top: 15px;
        }

        .header {
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="header">
    <table>
        <tr>
            <th>${user.getName()}</th>
        </tr>
        <tr>
            <th>${user.getEmail()}</th>
        </tr>
        <tr>
            <th>${user.getPhone()}</th>
        </tr>
        <tr>
            <th><a href="/change-pass-profile?email=${user.getEmail()}">Parolni tiklash</a></th>
        </tr>
    </table>
</div>

<div style="width: 70%; height: auto; overflow: auto;" class="cards-container">
    <table>
        <tr>
            <th>Number</th>
            <th>Tugash muddati</th>
            <th>Telefon</th>
            <th>Karta</th>
            <th>Xolati</th>
            <th>Balans</th>
            <th>Hisobni to'drish</th>
        </tr>

        <c:forEach items="${cards}" var="c">
            <tr>
                <td>${c.getNumber()}</td>
                <td>${c.getExpiryDate()}</td>
                <td>${c.getPhone()}</td>
                <td>${c.getType()}</td>
                <td>
                    <c:if test="${c.getIsActive()}">Aktiv</c:if>
                    <c:if test="${!c.getIsActive()}">Bloklangan</c:if>
                </td>
                <td>${c.getBalance()}</td>
                <td>
                    <c:if test="${c.getIsActive()}">
                        <form action="/block?id=${c.getId()}&status=${c.getIsActive()}" method="post">
                            <input type="hidden" name="method" value="block">
                            <button type="submit">Bloklash</button>
                        </form>
                    </c:if>
                    <c:if test="${!c.getIsActive()}">
                        <form action="/block?id=${c.getId()}&status=${c.getIsActive()}" method="post">
                            <input type="hidden" name="method" value="unblock">
                            <button type="submit">Blokdan chiqarish</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="button-container">
    <form action="/create-card">
        <button>Karta yaratish</button>
    </form>
    <form action="/history">
        <button>Tarixni ko'rish</button>
    </form>
</div>
<div>
    <a href="/welcome">Bosh menyu</a>
</div>
</body>
</html>
