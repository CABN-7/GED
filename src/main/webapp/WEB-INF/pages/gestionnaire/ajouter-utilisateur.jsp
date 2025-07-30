<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 29/07/2025
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un utilisateur</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .container {
            max-width: 600px;
            margin: auto;
            padding: 40px 20px;
        }

        h1 {
            color: #2c3e50;
            margin-bottom: 25px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        label {
            font-weight: bold;
        }

        input {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        button {
            background-color: #27ae60;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background-color: #1e8449;
        }

        a {
            margin-top: 10px;
            display: inline-block;
            color: #3498db;
            text-decoration: none;
        }

        .alert-success {
            color: green;
            margin-bottom: 10px;
        }

        .alert-error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>Créer un nouvel utilisateur</h1>

    <c:if test="${not empty message}">
        <div class="alert-success">${message}</div>
    </c:if>

    <c:if test="${not empty erreur}">
        <div class="alert-error">${erreur}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/gestionnaire/ajouter-utilisateur">
        <label for="nom">Nom :</label>
        <input type="text" name="nom" id="nom" required>

        <label for="prenom">Prénom :</label>
        <input type="text" name="prenom" id="prenom" required>

        <label for="email">Email :</label>
        <input type="email" name="email" id="email" required>

        <label for="motDePasse">Mot de passe :</label>
        <input type="password" name="motDePasse" id="motDePasse" required>

        <button type="submit">✅ Créer l’utilisateur</button>
        <a href="${pageContext.request.contextPath}/gestionnaire/utilisateurs">⬅ Retour</a>
    </form>
</div>

</body>
</html>
