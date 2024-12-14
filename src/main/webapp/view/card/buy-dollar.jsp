<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head   >
    <title>Buy</title>
    <link rel="stylesheet" href="/style.css">
    <style>
        .converter {
            max-width: 300px;
            margin: 0 auto;
        }
    </style>
</head>
<body>
<div style="margin: 20px 0px">
    <form action="/buy-dollar" method="post">
       O'tkazuvchi <select name="from">
            <c:forEach items="${cards}" var="c">
                <c:if test="${(c.getType() == 'HUMO' || c.getType() == 'UZCARD') && c.isActive}">
                    <option name="from" value="${c.getNumber()}">
                        <c:out value="${c.getNumber()}"/>
                        <c:out value="${c.getType()}"/>
                    </option>
                </c:if>
            </c:forEach>
        </select>
       Qabul qiluvchi <select name="to">
            <c:forEach items="${cards}" var="c">
                <c:if test="${(c.getType() == 'VISA' || c.getType() == 'MASTERCARD') && c.isActive}">
                    <option name="from" value="${c.getNumber()}">
                        <c:out value="${c.getNumber()}"/>
                        <c:out value="${c.getType()}"/>
                    </option>
                </c:if>
            </c:forEach>
        </select>
        <input name="amount" type="number" min="0.01" step="0.01" placeholder="Summani $ da kiriting" required>
        <button>O'tkazish</button>
    </form>

    <form action="/buy-dollar">
        <div class="converter">
            <input type="number" name="amount" min="0.01" step="0.01" placeholder="So'm kiriting" required>
            <button>Dollarga Otkazish</button>
        </div>
    </form>
    <c:if test="${not empty message}">
        <div class="error-message" style="color: green;">
                ${message}
        </div>
    </c:if>
</div>

</body>
</html>
