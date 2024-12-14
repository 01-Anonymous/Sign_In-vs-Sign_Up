<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>O'tkazmalar tarixi</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<div style="width: 70%; height: auto; overflow: auto;" class="button-container">
    <table style="width: 100%; height: auto;">
        <tr>
            <td style="width: 25%">Jo'natuvchi</td>
            <td style="width: 25%">Qabul qiluvchi</td>
            <td style="width: 25%">Summa</td>
            <td style="width: 25%">Tur</td>
            <td style="width: 25%">Sana</td>
        </tr>
        <c:forEach items="${history}" var="h">
            <tr>
                <th style="width: 25%"><c:out value="${h.getSetFrom()}"/></th>
                <th style="width: 25%"><c:out value="${h.getSetTo()}"/></th>
                <th style="width: 25%"><c:out value="${h.getAmount()}"/></th>
                <th style="width: 25%"><c:out value="${h.getSetType()}"/></th>
                <th style="width: 25%"><c:out value="${h.getSetDate()}"/></th>
            </tr>
        </c:forEach>
    </table>

</div>
<a href="/welcome">Bosh sahifa</a>
</body>
</html>
