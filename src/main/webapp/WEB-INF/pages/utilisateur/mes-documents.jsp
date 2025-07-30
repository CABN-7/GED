<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 30/07/2025
  Time: 00:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mes documents</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>📂 Mes documents</h1>

    <!-- ✅ Filtres -->
    <form method="get">
        <label for="titre">Titre :</label>
        <input type="text" name="titre" id="titre" value="${param.titre}">

        <label for="statut">Statut :</label>
        <select name="statut" id="statut">
            <option value="">-- Tous --</option>
            <option value="VALIDÉ" ${param.statut == 'VALIDÉ' ? 'selected' : ''}>Validé</option>
            <option value="REJETÉ" ${param.statut == 'REJETÉ' ? 'selected' : ''}>Rejeté</option>
            <option value="EN_ATTENTE" ${param.statut == 'EN_ATTENTE' ? 'selected' : ''}>En attente</option>
        </select>

        <button type="submit">🔍 Filtrer</button>
    </form>

    <br>

    <c:choose>
        <c:when test="${empty documents}">
            <p>Aucun document trouvé.</p>
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
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="doc" items="${documents}">
                    <tr>
                        <td>${doc.titre}</td>
                        <td>${doc.dateCreation}</td>
                        <td>${doc.type.libelle}</td>
                        <td>${doc.dossier.nom}</td>
                        <td>${doc.statut}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/fichier?nom=${doc.cheminFichier}"
                               target="_blank">📥 Télécharger</a>
                            |
                            <form action="${pageContext.request.contextPath}/utilisateur/supprimer-document"
                                  method="post" style="display:inline;"
                                  onsubmit="return confirm('Voulez-vous vraiment supprimer ce document ?');">
                                <input type="hidden" name="id" value="${doc.id}">
                                <button type="submit"
                                        style="color: red; background: none; border: none; cursor: pointer;">❌ Supprimer
                                </button>
                                <c:if test="${doc.statut == 'EN_ATTENTE'}">
                                    <a href="${pageContext.request.contextPath}/utilisateur/modifier-document?id=${doc.id}">✏️
                                        Modifier</a> |
                                </c:if>

                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <p><a href="${pageContext.request.contextPath}/utilisateur/dashboard">⬅ Retour au tableau de bord</a></p>
</div>

</body>
</html>
