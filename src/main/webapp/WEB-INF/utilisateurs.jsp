<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 21/07/2025
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des utilisateurs</title>
</head>
<body>
<h1>Utilisateurs</h1>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${utilisateurs}">
        <tr>
            <td>${user.id}</td>
            <td>${user.nom}</td>
            <td>${user.prenom}</td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Ajouter un utilisateur</h2>
<form method="post" action="${pageContext.request.contextPath}/utilisateurs">
    <input type="text" name="nom" placeholder="Nom" required />
    <input type="text" name="prenom" placeholder="Prénom" required />
    <input type="email" name="email" placeholder="Email" required />
    <input type="password" name="motDePasse" placeholder="Mot de passe" required />

    <select name="role" required>
        <option value="UTILISATEUR">Utilisateur</option>
        <option value="GESTIONNAIRE">Gestionnaire</option>
        <option value="ADMIN">Admin</option>
    </select>

    <button type="submit">Créer</button>
</form>


</body>
</html>

