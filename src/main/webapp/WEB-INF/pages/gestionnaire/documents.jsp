<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 29/07/2025
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des documents</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>Liste complète des documents</h1>

    <c:choose>
        <c:when test="${empty documents}">
            <p>Aucun document enregistré.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="5" cellspacing="0">
                <thead>
                <tr>
                    <th>Titre</th>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Propriétaire</th>
                    <th>Statut</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="d" items="${documents}">
                    <tr>
                        <td>${d.titre}</td>
                        <td>${d.dateCreation}</td>
                        <td>${d.type.libelle}</td>
                        <td>${d.proprietaire.nom} ${d.proprietaire.prenom}</td>
                        <td>${d.statut}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/gestionnaire/document-details?id=${d.id}">
                                🔍 Détails
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <p><a href="${pageContext.request.contextPath}/gestionnaire/dashboard">⬅ Retour au tableau de bord</a></p>
</div>

</body>
</html>

