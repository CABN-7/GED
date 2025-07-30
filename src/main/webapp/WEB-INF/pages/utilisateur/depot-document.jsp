<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 29/07/2025
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Déposer un document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

    <style>
        #newFolderDiv {
            margin-top: 10px;
            padding: 10px;
            background-color: #f9f9f9;
            border-left: 3px solid #3498db;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>Déposer un nouveau document</h1>

    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <form method="post" enctype="multipart/form-data">
        <label for="titre">Titre :</label>
        <input type="text" name="titre" id="titre" required><br>

        <label for="type">Type :</label>
        <select name="type" id="type" required>
            <c:forEach var="t" items="${types}">
                <option value="${t.id}">${t.libelle}</option>
            </c:forEach>
            <c:if test="${empty types}">
                <option disabled>(Aucun type disponible)</option>
            </c:if>
        </select><br>

        <label for="dossier">Dossier :</label>
        <select name="dossier" id="dossier" required onchange="toggleNewFolderInput()">
            <option value="">-- Choisissez un dossier --</option>
            <option value="racine">Racine (dossier par défaut)</option>
            <c:forEach var="d" items="${dossiers}">
                <option value="${d.id}">${d.nom}</option>
            </c:forEach>
            <c:if test="${empty dossiers}">
                <option disabled>(Aucun dossier disponible)</option>
            </c:if>
            <option value="nouveau">-- Créer un nouveau dossier --</option>
        </select><br>

        <div id="newFolderDiv" style="display:none;">
            <label for="nouveauDossier">Nom du nouveau dossier :</label>
            <input type="text" name="nouveauDossier" id="nouveauDossier"><br>
        </div>

        <label for="fichier">Fichier :</label>
        <input type="file" name="fichier" id="fichier" required><br><br>

        <button type="submit">📤 Déposer</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/utilisateur/dashboard">⬅ Retour au tableau de bord</a></p>
</div>

<script>
    function toggleNewFolderInput() {
        var select = document.getElementById('dossier');
        var newFolderDiv = document.getElementById('newFolderDiv');
        var input = document.getElementById('nouveauDossier');
        if (select.value === 'nouveau') {
            newFolderDiv.style.display = 'block';
            input.required = true;
        } else {
            newFolderDiv.style.display = 'none';
            input.required = false;
            input.value = '';
        }
    }

    // Validation JS en plus pour éviter soumission vide (sécurité supplémentaire)
    document.querySelector("form").addEventListener("submit", function (e) {
        var select = document.getElementById('dossier');
        var input = document.getElementById('nouveauDossier');
        if (select.value === 'nouveau' && input.value.trim() === "") {
            alert("Veuillez saisir un nom pour le nouveau dossier.");
            e.preventDefault();
        }
    });
</script>

</body>
</html>


