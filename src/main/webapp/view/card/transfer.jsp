

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Transfer</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<div style="margin: 20px 0px">
    <form action="/transfer" method="post">
        <input name="to" type="text" minlength="19" maxlength="19" placeholder="Qabul qiluvchi(**** **** **** ****)"
               required>
        <input name="amount" type="number" min="0.01" step="0.01" placeholder="Summani kiriting" required>
        <select name="from">
            <c:forEach items="${cards}" var="c">
                <option name="from" value="${c.getNumber()}">
                    <c:out value="${c.getNumber()} "/>
                    <c:out value="${c.getType()}"/>
                </option>
            </c:forEach>
        </select>

        <button>O'tkazish</button>
    </form>
</div>
</body>
</html>
