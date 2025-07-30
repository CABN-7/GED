<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 28/07/2025
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Changer de mot de passe</title></head>
<body>
<h2>Changer de mot de passe</h2>

<c:if test="${not empty error}">
    <p style="color: red">${error}</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/changer-mot-de-passe">
    <label>Nouveau mot de passe :</label><br/>
    <input type="password" name="nouveauMotDePasse" required/><br/>
    <label>Confirmer le mot de passe :</label><br/>
    <input type="password" name="confirmation" required/><br/><br/>
    <button type="submit">Changer</button>
</form>
</body>
</html>

