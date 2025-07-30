<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 30/07/2025
  Time: 00:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modifier le document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>✏️ Modifier le document</h1>

    <form method="post">
        <input type="hidden" name="id" value="${document.id}">

        <label for="titre">Titre :</label>
        <input type="text" name="titre" id="titre" value="${document.titre}" required><br>

        <label for="type">Type :</label>
        <select name="type" id="type" required>
            <c:forEach var="t" items="${types}">
                <option value="${t.id}" ${t.id == document.type.id ? 'selected' : ''}>${t.libelle}</option>
            </c:forEach>
        </select><br>

        <label for="dossier">Dossier :</label>
        <select name="dossier" id="dossier" required>
            <c:forEach var="d" items="${dossiers}">
                <option value="${d.id}" ${d.id == document.dossier.id ? 'selected' : ''}>${d.nom}</option>
            </c:forEach>
        </select><br>

        <br>
        <button type="submit">💾 Enregistrer</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/utilisateur/mes-documents">⬅ Retour à mes documents</a></p>
</div>

</body>
</html>

