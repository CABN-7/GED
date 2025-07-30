<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 29/07/2025
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détails du document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>Détails du document</h1>

    <c:choose>
        <c:when test="${not empty document}">
            <ul>
                <li><strong>Titre :</strong> ${document.titre}</li>
                <li><strong>Date de création :</strong> ${document.dateCreation}</li>
                <li><strong>Type :</strong> ${document.type.libelle}</li>
                <li><strong>Dossier :</strong> ${document.dossier.nom}</li>
                <li><strong>Statut :</strong> ${document.statut}</li>
                <li><strong>Chemin du fichier :</strong> ${document.cheminFichier}</li>
            </ul>

            <a href="${pageContext.request.contextPath}/fichier?nom=${document.cheminFichier}">
                📥 Télécharger
            </a>
        </c:when>
        <c:otherwise>
            <p>Document introuvable.</p>
        </c:otherwise>
    </c:choose>

    <p><a href="${pageContext.request.contextPath}/utilisateur/dashboard">⬅ Retour au tableau de bord</a></p>
</div>

</body>
</html>

