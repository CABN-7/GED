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
<html>
<head>
    <title>Admin - Gestion des utilisateurs</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 1em;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        form.inline {
            display: inline;
        }

        .btn {
            padding: 5px 10px;
            margin: 2px;
            border: none;
            cursor: pointer;
        }

        .btn-activate {
            background-color: #4CAF50;
            color: white;
        }

        .btn-deactivate {
            background-color: #f44336;
            color: white;
        }

        .btn-delete {
            background-color: #555;
            color: white;
        }

        .btn-submit {
            background-color: #008CBA;
            color: white;
        }

        .pagination {
            margin-top: 1em;
        }

        .pagination a {
            margin: 0 4px;
            padding: 6px 12px;
            border: 1px solid #ddd;
            text-decoration: none;
            color: #333;
        }

        .pagination a.current {
            background-color: #008CBA;
            color: white;
            border-color: #008CBA;
        }
    </style>
</head>
<body>
<h1>Tableau de bord Admin - Gestion des utilisateurs</h1>

<!-- Statistiques simples -->
<p>
    Total utilisateurs : <strong>${totalUtilisateurs + totalGestionnaires + totalAdmins}</strong> |
    Administrateurs : <strong>${totalAdmins}</strong> |
    Gestionnaires : <strong>${totalGestionnaires}</strong> |
    Utilisateurs : <strong>${totalUtilisateurs}</strong>
</p>

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
                <form class="inline" method="post" action="${pageContext.request.contextPath}/admin/utilisateurs">
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <input type="hidden" name="action" value="${user.actif ? 'desactiver' : 'activer'}"/>
                    <button type="submit" class="btn ${user.actif ? 'btn-deactivate' : 'btn-activate'}">
                            ${user.actif ? "Désactiver" : "Activer"}
                    </button>
                </form>
                <form class="inline" method="post" action="${pageContext.request.contextPath}/admin/utilisateurs"
                      onsubmit="return confirm('Confirmez-vous la suppression de cet utilisateur ?');">
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <input type="hidden" name="action" value="supprimer"/>
                    <button type="submit" class="btn btn-delete">Supprimer</button>
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

<hr/>

<!-- Formulaire de création d’un gestionnaire -->
<h2>Créer un nouveau gestionnaire</h2>
<form method="post" action="${pageContext.request.contextPath}/admin/utilisateurs">
    <input type="hidden" name="action" value="creer"/>
    <label for="nom">Nom :</label><br/>
    <input type="text" id="nom" name="nom" required/><br/>

    <label for="prenom">Prénom :</label><br/>
    <input type="text" id="prenom" name="prenom" required/><br/>

    <label for="email">Email :</label><br/>
    <input type="email" id="email" name="email" required/><br/>

    <label for="motDePasse">Mot de passe :</label><br/>
    <input type="password" id="motDePasse" name="motDePasse" required/><br/><br/>

    <button type="submit" class="btn btn-submit">Créer le gestionnaire</button>
</form>

<c:if test="${not empty sessionScope.successMessage}">
    <p style="color:green">${sessionScope.successMessage}</p>
    <c:remove var="successMessage" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.errorMessage}">
    <p style="color:red">${sessionScope.errorMessage}</p>
    <c:remove var="errorMessage" scope="session"/>
</c:if>


</body>
</html>
