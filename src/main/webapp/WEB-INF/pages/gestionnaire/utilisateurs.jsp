<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 29/07/2025
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des utilisateurs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>Liste des utilisateurs simples</h1>
    <a href="${pageContext.request.contextPath}/gestionnaire/ajouter-utilisateur">➕ Ajouter un utilisateur</a>

    <c:choose>
        <c:when test="${empty utilisateurs}">
            <p>Aucun utilisateur trouvé.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="5" cellspacing="0">
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Email</th>
                    <th>Date création</th>
                    <th>Actif</th>
                    <th>Détails</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="u" items="${utilisateurs}">
                    <tr>
                        <td>${u.nom}</td>
                        <td>${u.prenom}</td>
                        <td>${u.email}</td>
                        <td>${u.dateCreation}</td>
                        <td><c:choose>
                            <c:when test="${u.actif}">Oui</c:when>
                            <c:otherwise>Non</c:otherwise>
                        </c:choose></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/gestionnaire/utilisateur-details?id=${u.id}">
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

