<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 29/07/2025
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détail utilisateur</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .container {
            max-width: 700px;
            margin: auto;
            padding: 40px 20px;
        }

        h1 {
            color: #2c3e50;
        }

        ul {
            list-style: none;
            padding: 0;
            margin: 20px 0;
        }

        ul li {
            padding: 10px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 6px;
            margin-bottom: 10px;
        }

        strong {
            color: #34495e;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            color: #3498db;
            text-decoration: none;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>Détails de l'utilisateur</h1>

    <c:choose>
        <c:when test="${not empty utilisateur}">
            <ul>
                <li><strong>Nom :</strong> ${utilisateur.nom}</li>
                <li><strong>Prénom :</strong> ${utilisateur.prenom}</li>
                <li><strong>Email :</strong> ${utilisateur.email}</li>
                <li><strong>Rôle :</strong> ${utilisateur.role}</li>
                <li><strong>Actif :</strong>
                    <c:choose>
                        <c:when test="${utilisateur.actif}">Oui</c:when>
                        <c:otherwise>Non</c:otherwise>
                    </c:choose>
                </li>
                <li><strong>Date de création :</strong> ${utilisateur.dateCreation}</li>
            </ul>
        </c:when>
        <c:otherwise>
            <p style="color: red;">Utilisateur introuvable.</p>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/gestionnaire/dashboard">⬅ Retour au tableau de bord</a>
</div>

</body>
</html>
