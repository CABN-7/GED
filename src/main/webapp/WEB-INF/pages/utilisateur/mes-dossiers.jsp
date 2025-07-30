<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mes dossiers</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        ul.tree, ul.tree ul {
            list-style: none;
            padding-left: 1em;
            border-left: 1px dashed #ccc;
        }
        .document {
            margin-left: 1em;
        }
        .toggle-button {
            cursor: pointer;
            font-weight: bold;
        }
        .hidden {
            display: none;
        }
        .folder-name:hover {
            text-decoration: underline;
            cursor: pointer;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>📁 Mes dossiers</h1>

    <!-- 🟩 Option de masquage -->
    <label><input type="checkbox" id="hideEmpty"> Masquer les dossiers vides</label>

    <ul class="tree">
        <c:forEach var="dossier" items="${dossiers}">
            <jsp:include page="/WEB-INF/fragments/dossier-recursif.jsp">
                <jsp:param name="dossierId" value="${dossier.id}"/>
                <jsp:param name="level" value="0"/>
            </jsp:include>
        </c:forEach>
    </ul>

    <p><a href="${pageContext.request.contextPath}/utilisateur/dashboard">⬅ Retour</a></p>
</div>

<script>
    document.querySelectorAll('.toggle-button').forEach(btn => {
        btn.addEventListener('click', () => {
            const ul = btn.parentElement.querySelector('ul');
            if (ul) ul.classList.toggle('hidden');
        });
    });

    document.getElementById('hideEmpty').addEventListener('change', function () {
        const show = !this.checked;
        document.querySelectorAll('.folder-block').forEach(folder => {
            const hasDoc = folder.querySelectorAll('li.document').length > 0;
            const hasSub = folder.querySelectorAll('li.folder-block').length > 0;
            folder.style.display = (show || hasDoc || hasSub) ? 'block' : 'none';
        });
    });
</script>

</body>
</html>
