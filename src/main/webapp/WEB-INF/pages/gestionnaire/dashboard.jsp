<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 29/07/2025
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Gestionnaire</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>Bienvenue sur le tableau de bord du gestionnaire</h1>

    <div class="stats">
        <h2>Statistiques</h2>
        <ul>
            <li>Total de documents : <strong>${totalDocuments}</strong></li>
            <li>Documents validés : <strong>${documentsValidés}</strong></li>
            <li>Documents rejetés : <strong>${documentsRejetés}</strong></li>
            <li>En attente de validation : <strong>${documentsEnAttente}</strong></li>
        </ul>
    </div>

    <div class="section">
        <h2>Filtres de recherche</h2>
        <form method="get" action="${pageContext.request.contextPath}/gestionnaire/dashboard" class="filter-form">
            <label for="titre">Titre :</label>
            <input type="text" name="titre" id="titre" value="${titreFiltre}"/>

            <label for="proprietaire">Propriétaire :</label>
            <input type="text" name="proprietaire" id="proprietaire" value="${proprietaireFiltre}"/>

            <label for="statut">Statut :</label>
            <select name="statut" id="statut">
                <option value="">-- Tous --</option>
                <option value="EN_ATTENTE" ${statutFiltre == 'EN_ATTENTE' ? 'selected' : ''}>En attente</option>
                <option value="VALIDÉ" ${statutFiltre == 'VALIDÉ' ? 'selected' : ''}>Validé</option>
                <option value="REJETÉ" ${statutFiltre == 'REJETÉ' ? 'selected' : ''}>Rejeté</option>
            </select>

            <button type="submit">🔍 Rechercher</button>
        </form>
    </div>

    <div class="section">
        <h2>Documents</h2>
        <c:choose>
            <c:when test="${empty documents}">
                <p>Aucun document trouvé avec les critères actuels.</p>
            </c:when>
            <c:otherwise>
                <table border="1" cellpadding="5" cellspacing="0">
                    <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Propriétaire</th>
                        <th>Date</th>
                        <th>Type</th>
                        <th>Dossier</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="doc" items="${documents}">
                        <tr>
                            <td>${doc.titre}</td>
                            <td>${doc.proprietaire.prenom} ${doc.proprietaire.nom}</td>
                            <td>${doc.dateCreation}</td>
                            <td>${doc.type.libelle}</td>
                            <td>${doc.dossier.nom}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/gestionnaire/traiter-document"
                                      method="post" style="display:inline">
                                    <input type="hidden" name="id" value="${doc.id}"/>
                                    <button type="submit" name="action" value="valider">✅ Valider</button>
                                    <button type="submit" name="action" value="rejeter">❌ Rejeter</button>
                                </form>
                                <a href="${pageContext.request.contextPath}/gestionnaire/document-details?id=${doc.id}">🔍
                                    Détails</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="section">
        <h2>Utilisateurs simples</h2>
        <c:choose>
            <c:when test="${empty utilisateursSimples}">
                <p>Aucun utilisateur trouvé.</p>
            </c:when>
            <c:otherwise>
                <ul>
                    <c:forEach var="u" items="${utilisateursSimples}">
                        <li>
                            <a href="${pageContext.request.contextPath}/gestionnaire/utilisateur-details?id=${u.id}">
                                    ${u.nom} ${u.prenom}
                            </a> - ${u.email}
                        </li>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>
    </div>

</div>

</body>
</html>
