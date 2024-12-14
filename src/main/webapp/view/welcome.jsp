<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main</title>
    <link rel="stylesheet" href="/style.css">
    <style>
        body {
            margin: 0;
            padding: 0;
            height: 100vh;
            overflow-y: auto;
        }

        .header {
            position: absolute;
            top: 10px;
            right: 20px;
            margin: 0;
            padding: 0;
        }

        .cards-container {
            width: 70%;
            max-height: 300px;
            overflow-y: auto;
            border: 1px solid #ccc;
            margin-top: 20px;
        }

        .cards-container table {
            width: 100%;
            border-collapse: collapse;
        }

        .cards-container th, .cards-container td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: left;
        }
    </style>
</head>
<body>
<div>
    <a class="header" href="/profile">
        <h3>Shaxsiy kabinet</h3>
    </a>
</div>
<div>
    <table style="width: 100%">
        <tr>
            <th>Umumiy balans</th>
        </tr>
        <tr>
            <th>${balanceS} so'm</th>
        </tr>
        <tr>
            <th>${balanceD} $</th>
        </tr>
    </table>
</div>
<div class="cards-container">
    <table>
        <tr>
            <th>Number</th>
            <th>Tugash muddati</th>
            <th>Karta</th>
            <th>Balans</th>
            <th>Hisobni to'drish</th>
        </tr>
        <c:forEach items="${cards}" var="c">
            <tr>
                <td>${c.getNumber()}</td>
                <td>${c.getExpiryDate()}</td>
                <td>${c.getType()}</td>
                <td>${c.getBalance()}
                    <c:if test="${c.getType() == 'HUMO' || c.getType() == 'UZCARD'}">
                        so'm
                    </c:if>
                    <c:if test="${c.getType() != 'HUMO' && c.getType() != 'UZCARD'}">
                        '$'
                    </c:if>
                </td>
                <td>
                    <a href="/fill-balance?number=${c.getNumber()}">Hisobni to'dirish</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="button-container">
    <form action="/transfer">
        <button>Pul o'tkazish</button>
    </form>
    <form action="/buy-dollar">
        <button>$ sotib olish</button>
    </form>
</div>
</body>
</html>
