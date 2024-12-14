
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Confirmation code</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<div>
    <form action="/confirmation-code" method="POST">
        <input type="text" maxlength="6" name="code" placeholder="Kodni kiritng" required>
        <input type="hidden" name="email" value="${email}">
        <button type="submit">Tasdiqlash</button>
    </form>
</div>
    <c:if test="${not empty errorMessage}">
        <div class="error-message" style="color: red;">
                ${errorMessage}
        </div>
    </c:if>
</body>
</html>
