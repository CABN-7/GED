<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 29/07/2025
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Uploader un fichier</title>
</head>
<body>
<h1>Ajouter un nouveau document</h1>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>
<c:if test="${not empty success}">
    <p style="color:green">${success}</p>
</c:if>

<form action="${pageContext.request.contextPath}/fichier" method="post" enctype="multipart/form-data">
    <label>Titre :</label>
    <input type="text" name="titre" required/><br/><br/>

    <label>Choisir un fichier :</label>
    <input type="file" name="fichier" required/><br/><br/>

    <button type="submit">📤 Envoyer</button>
</form>

<a href="${pageContext.request.contextPath}/pages/utilisateur/dashboard.jsp">⬅ Retour au dashboard</a>
</body>
</html>

