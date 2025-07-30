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
    <style>
        .container {
            max-width: 1100px;
            margin: auto;
            padding: 40px 20px;
        }

        h1, h2 {
            color: #2c3e50;
        }

        .stats ul {
            list-style: none;
            padding: 0;
            background-color: #ecf0f1;
            border-radius: 8px;
            padding: 15px;
        }

        .stats li {
            margin-bottom: 8px;
            font-size: 1.1em;
        }

        .filter-form {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
            gap: 15px;
            margin-bottom: 25px;
            margin-top: 10px;
        }

        .filter-form input, .filter-form select {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        .filter-form button {
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 6px;
            padding: 10px;
            cursor: pointer;
        }

        .filter-form button:hover {
            background-color: #2980b9;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        table th, table td {
            padding: 12px;
            border: 1px solid #ddd;
        }

        table th {
            background-color: #34495e;
            color: white;
        }

        .section {
            margin-top: 40px;
        }

        ul li a {
            text-decoration: none;
            color: #2980b9;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>Bienvenue sur le tableau de bord du gestionnaire</h1>

    <div class="stats">
        <h2>📊 Statistiques</h2>
        <ul>
            <li>Total de documents : <strong>${totalDocuments}</strong></li>
            <li>Documents validés : <strong>${documentsValidés}</strong></li>
            <li>Documents rejetés : <strong>${documentsRejetés}</strong></li>
            <li>En attente de validation : <strong>${documentsEnAttente}</strong></li>
        </ul>
    </div>

    <div class="section">
        <h2>🔎 Filtres de recherche</h2>
        <form method="get" action="${pageContext.request.contextPath}/gestionnaire/dashboard" class="filter-form">
            <input type="text" name="titre" placeholder="Titre..." value="${titreFiltre}" />
            <input type="text" name="proprietaire" placeholder="Propriétaire..." value="${proprietaireFiltre}" />
            <select name="statut">
                <option value="">-- Tous les statuts --</option>
                <option value="EN_ATTENTE" ${statutFiltre == 'EN_ATTENTE' ? 'selected' : ''}>En attente</option>
                <option value="VALIDÉ" ${statutFiltre == 'VALIDÉ' ? 'selected' : ''}>Validé</option>
                <option value="REJETÉ" ${statutFiltre == 'REJETÉ' ? 'selected' : ''}>Rejeté</option>
            </select>
            <button type="submit">🔍 Rechercher</button>
        </form>
    </div>

    <div class="section">
        <h2>📁 Documents</h2>
        <c:choose>
            <c:when test="${empty documents}">
                <p>Aucun document trouvé avec les critères actuels.</p>
            </c:when>
            <c:otherwise>
                <table>
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
                                <form action="${pageContext.request.contextPath}/gestionnaire/traiter-document" method="post" style="display:inline">
                                    <input type="hidden" name="id" value="${doc.id}"/>
                                    <button type="submit" name="action" value="valider">✅</button>
                                    <button type="submit" name="action" value="rejeter">❌</button>
                                </form>
                                <a href="${pageContext.request.contextPath}/gestionnaire/document-details?id=${doc.id}">🔍 Détails</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="section">
        <h2>👤 Utilisateurs simples</h2>
        <c:choose>
            <c:when test="${empty utilisateursSimples}">
                <p>Aucun utilisateur trouvé.</p>
            </c:when>
            <c:otherwise>
                <ul>
                    <c:forEach var="u" items="${utilisateursSimples}">
                        <li><a href="${pageContext.request.contextPath}/gestionnaire/utilisateur-details?id=${u.id}">
                                ${u.nom} ${u.prenom}
                        </a> - ${u.email}</li>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
