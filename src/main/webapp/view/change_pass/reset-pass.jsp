
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Reset password</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<div>
    <form action="/change-pass" method="POST">
        <input type="password" name="newPassword" placeholder="Yangi parolni kiriting"
               pattern="^(?=.*\d)[A-Za-z\d]{8,}$" required>
        <input type="hidden" name="email" value="${email}" >
        <button type="submit">Parolni yangilash</button>
    </form>
</div>
<div>
    <c:if test="${not empty errorMesage}">
        <div class="error-message" style="color: red;">
                ${errorMessage}
        </div>
    </c:if>
</div>
</body>
</html>
