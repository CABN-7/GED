<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 26/07/2025
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Admin - Gestion des utilisateurs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>⚙️ Tableau de bord Administrateur</h1>

    <!-- Statistiques -->
    <p>
        👥 Total utilisateurs : <strong>${totalUtilisateurs + totalGestionnaires + totalAdmins}</strong> |
        🛠️ Gestionnaires : <strong>${totalGestionnaires}</strong> |
        👤 Utilisateurs : <strong>${totalUtilisateurs}</strong> |
        👑 Admins : <strong>${totalAdmins}</strong>
    </p>

    <!-- Messages de session -->
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="success-message">${sessionScope.successMessage}</div>
        <c:remove var="successMessage" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.errorMessage}">
        <div class="error-message">${sessionScope.errorMessage}</div>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>

    <!-- Liste des utilisateurs -->
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Email</th>
            <th>Rôle</th>
            <th>Actif</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${utilisateurs}">
            <tr>
                <td>${user.id}</td>
                <td>${fn:escapeXml(user.nom)}</td>
                <td>${fn:escapeXml(user.prenom)}</td>
                <td>${fn:escapeXml(user.email)}</td>
                <td>${user.role}</td>
                <td>${user.actif ? "Oui" : "Non"}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin/utilisateurs"
                          style="display:inline;">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <input type="hidden" name="action" value="${user.actif ? 'desactiver' : 'activer'}"/>
                        <button type="submit" class="btn ${user.actif ? 'btn-danger' : 'btn-success'}">
                                ${user.actif ? 'Désactiver' : 'Activer'}
                        </button>
                    </form>
                    <form method="post" action="${pageContext.request.contextPath}/admin/utilisateurs"
                          style="display:inline;"
                          onsubmit="return confirm('Confirmez-vous la suppression de cet utilisateur ?');">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <input type="hidden" name="action" value="supprimer"/>
                        <button type="submit" class="btn btn-secondary">Supprimer</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Pagination -->
    <div class="pagination">
        <c:if test="${page > 1}">
            <a href="${pageContext.request.contextPath}/admin/utilisateurs?page=${page - 1}">&laquo; Précédent</a>
        </c:if>
        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="${pageContext.request.contextPath}/admin/utilisateurs?page=${i}"
               class="${i == page ? 'current' : ''}">${i}</a>
        </c:forEach>
        <c:if test="${page < totalPages}">
            <a href="${pageContext.request.contextPath}/admin/utilisateurs?page=${page + 1}">Suivant &raquo;</a>
        </c:if>
    </div>

    <!-- Formulaire de création d’un utilisateur -->
    <hr>
    <h2>➕ Créer un nouveau compte</h2>
    <form method="post" action="${pageContext.request.contextPath}/admin/utilisateurs">
        <input type="hidden" name="action" value="creer"/>

        <label>Nom :</label><br>
        <input type="text" name="nom" required><br>

        <label>Prénom :</label><br>
        <input type="text" name="prenom" required><br>

        <label>Email :</label><br>
        <input type="email" name="email" required><br>

        <label>Mot de passe :</label><br>
        <input type="password" name="motDePasse" required><br>

        <label>Rôle :</label><br>
        <select name="role" required>
            <option value="GESTIONNAIRE" selected>Gestionnaire</option>
            <option value="ADMIN">Admin</option>
            <option value="UTILISATEUR">Utilisateur</option>
        </select><br><br>

        <button type="submit" class="btn btn-primary">Créer le compte</button>
    </form>

    <div style="margin-top: 30px;">
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">
            ⬅ Retour au tableau de bord
        </a>
    </div>
</div>

</body>
</html>
