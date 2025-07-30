<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 21/07/2025
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des utilisateurs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .container {
            max-width: 900px;
            margin: 40px auto;
            padding: 30px;
            background: #f9f9f9;
            border-radius: 12px;
            box-shadow: 0 3px 8px rgba(0, 0, 0, 0.1);
        }

        h1, h2 {
            text-align: center;
            color: #2c3e50;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #2c3e50;
            color: white;
        }

        form {
            margin-top: 30px;
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        input, select {
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }

        button {
            padding: 10px;
            background-color: #2c3e50;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #34495e;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>Liste des utilisateurs</h1>

    <table>
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
        <input type="text" name="nom" placeholder="Nom" required/>
        <input type="text" name="prenom" placeholder="Prénom" required/>
        <input type="email" name="email" placeholder="Email" required/>
        <input type="password" name="motDePasse" placeholder="Mot de passe" required/>

        <select name="role" required>
            <option value="UTILISATEUR">Utilisateur</option>
            <option value="GESTIONNAIRE">Gestionnaire</option>
            <option value="ADMIN">Admin</option>
        </select>

        <button type="submit">✅ Créer</button>
    </form>
</div>

</body>
</html>
