<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 29/07/2025
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Uploader un fichier</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .upload-container {
            max-width: 600px;
            margin: 60px auto;
            background: #f9f9f9;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 16px;
        }

        label {
            font-weight: bold;
            color: #34495e;
        }

        input[type="text"],
        input[type="file"] {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        button {
            padding: 10px;
            background-color: #2c3e50;
            color: #fff;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background-color: #34495e;
        }

        .message {
            text-align: center;
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .success {
            background-color: #eafaf1;
            color: #27ae60;
        }

        .error {
            background-color: #fdecea;
            color: #e74c3c;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            color: #2c3e50;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="upload-container">
    <h1>Ajouter un nouveau document</h1>

    <c:if test="${not empty error}">
        <div class="message error">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="message success">${success}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/fichier" method="post" enctype="multipart/form-data">
        <label for="titre">Titre :</label>
        <input type="text" name="titre" id="titre" required>

        <label for="fichier">Choisir un fichier :</label>
        <input type="file" name="fichier" id="fichier" required>

        <button type="submit">📤 Envoyer</button>
    </form>

    <a href="${pageContext.request.contextPath}/utilisateur/dashboard">⬅ Retour au tableau de bord</a>
</div>

</body>
</html>
