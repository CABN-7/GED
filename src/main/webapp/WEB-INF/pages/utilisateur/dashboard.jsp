<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 29/07/2025
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tableau de bord - Utilisateur</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>Bienvenue sur votre tableau de bord</h1>

    <div class="stats">
        <h2>Statistiques</h2>
        <ul>
            <li>Total de documents : <strong>${total}</strong></li>
            <li>Documents validés : <strong>${valides}</strong></li>
            <li>Documents rejetés : <strong>${rejetes}</strong></li>
            <li>En attente : <strong>${attente}</strong></li>
        </ul>
    </div>

    <div class="section">
        <h2>Mes documents</h2>
        <c:choose>
            <c:when test="${empty documents}">
                <p>Vous n'avez encore déposé aucun document.</p>
            </c:when>
            <c:otherwise>
                <table border="1" cellpadding="5" cellspacing="0">
                    <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Date</th>
                        <th>Type</th>
                        <th>Dossier</th>
                        <th>Statut</th>
                        <th>Détails</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="doc" items="${documents}">
                        <tr>
                            <td>${doc.titre}</td>
                            <td>${doc.dateCreation}</td>
                            <td><c:out value="${doc.type != null ? doc.type.libelle : 'N/A'}"/></td>
                            <td>${doc.dossier.nom}</td>
                            <td>${doc.statut}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/utilisateur/document-details?id=${doc.id}">
                                    🔍 Détails
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>

