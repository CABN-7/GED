<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 26/07/2025
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Erreur</title>
</head>
<body>
<h1>Une erreur est survenue</h1>
<p>${message}</p>
<a href="${pageContext.request.contextPath}/admin/utilisateurs">Retour à la gestion des utilisateurs</a>
</body>
</html>

