
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Confirm code</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<div>
    <form action="/check-confirmation-code" method="POST">
        <input type="text" maxlength="6" name="code" placeholder="Kodni kiritng" required>
        <input type="hidden" name="email" value="${email}">
        <button type="submit">Tasdiqlash</button>
    </form>
</div>
<div>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">
            ${errorMessage}
        </div>
    </c:if>
</div>
</body>
</html>
