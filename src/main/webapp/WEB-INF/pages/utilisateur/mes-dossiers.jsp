<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% response.setCharacterEncoding("UTF-8"); %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mes dossiers</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .tree {
            list-style: none;
            padding-left: 0;
        }

        .tree ul {
            list-style: none;
            padding-left: 2em;
            border-left: 1px dashed #ccc;
        }

        .folder-block {
            margin: 5px 0;
            padding: 5px;
        }

        .racine-folder {
            background-color: #f0f8ff;
            border: 1px solid #d0e7ff;
            border-radius: 5px;
            padding: 10px;
        }

        .sous-dossier {
            background-color: #f9f9f9;
            border-left: 3px solid #007bff;
            margin: 3px 0;
        }

        .document {
            margin: 3px 0;
            padding: 3px 10px;
            background-color: #fff;
            border-left: 2px solid #28a745;
        }

        .empty-folder {
            font-style: italic;
            color: #666;
            padding: 10px;
        }

        .toggle-button {
            cursor: pointer;
            font-weight: bold;
            margin-right: 5px;
            user-select: none;
        }

        .toggle-button:hover {
            color: #007bff;
        }

        .hidden {
            display: none;
        }

        .folder-name {
            font-weight: bold;
            margin-right: 10px;
        }

        .folder-name:hover {
            text-decoration: underline;
            cursor: pointer;
        }

        .actions {
            margin-left: 10px;
        }

        .actions a {
            margin: 0 5px;
            text-decoration: none;
            cursor: pointer;
        }

        .success-message {
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            border: 1px solid #c3e6cb;
            border-radius: 5px;
            margin: 10px 0;
        }

        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border: 1px solid #f5c6cb;
            border-radius: 5px;
            margin: 10px 0;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

<div class="container">
    <h1>📁 Mes dossiers</h1>

    <!-- Messages de succès/erreur -->
    <c:if test="${not empty success}">
        <div class="success-message">${success}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <!-- Options d'affichage -->
    <div style="margin: 20px 0;">
        <label>
            <input type="checkbox" id="hideEmpty">
            Masquer les dossiers vides
        </label>
        <label style="margin-left: 20px;">
            <input type="checkbox" id="expandAll">
            Développer tous les dossiers
        </label>
    </div>

    <!-- Arborescence des dossiers -->
    <ul class="tree">
        <c:choose>
            <c:when test="${not empty dossierRacine}">
                <c:set var="dossier" value="${dossierRacine}" scope="request"/>
                <jsp:include page="/WEB-INF/fragments/dossier-racine.jsp"/>
            </c:when>
            <c:otherwise>
                <li class="empty-folder">
                    <p>Aucun dossier trouvé. Le dossier racine sera créé automatiquement.</p>
                    <a href="${pageContext.request.contextPath}/utilisateur/mes-dossiers"
                       class="btn btn-primary">Actualiser</a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>

    <div style="margin-top: 30px;">
        <a href="${pageContext.request.contextPath}/utilisateur/dashboard" class="btn btn-secondary">
            ⬅ Retour au tableau de bord
        </a>
    </div>
</div>

<script>
    // Gestion des boutons toggle
    document.querySelectorAll('.toggle-button').forEach(btn => {
        btn.addEventListener('click', function () {
            const ul = this.parentElement.querySelector('ul');
            if (ul) {
                ul.classList.toggle('hidden');
                // Changer l'icône
                this.textContent = ul.classList.contains('hidden') ? '▶' : '▼';
            }
        });
    });

    // Option masquer les dossiers vides
    document.getElementById('hideEmpty').addEventListener('change', function () {
        const hide = this.checked;
        document.querySelectorAll('.folder-block').forEach(folder => {
            const hasDoc = folder.querySelectorAll('.document').length > 0;
            const hasSub = folder.querySelectorAll('.folder-block').length > 0;

            if (hide && !hasDoc && !hasSub && !folder.classList.contains('racine-folder')) {
                folder.style.display = 'none';
            } else {
                folder.style.display = 'block';
            }
        });
    });

    // Option développer tous les dossiers
    document.getElementById('expandAll').addEventListener('change', function () {
        const expand = this.checked;
        document.querySelectorAll('.folder-block ul').forEach(ul => {
            if (expand) {
                ul.classList.remove('hidden');
            } else {
                ul.classList.add('hidden');
            }
        });

        // Mettre à jour les icônes
        document.querySelectorAll('.toggle-button').forEach(btn => {
            const ul = btn.parentElement.querySelector('ul');
            if (ul) {
                btn.textContent = expand ? '▼' : '▶';
            }
        });
    });

    // Fonctions pour les actions sur les dossiers
    function renamerDossier(id, nomActuel) {
        const nouveauNom = prompt('Nouveau nom pour le dossier:', nomActuel);
        if (nouveauNom && nouveauNom.trim() !== '' && nouveauNom !== nomActuel) {
            // TODO: Implémenter la fonction de renommage
            alert('Fonction de renommage à implémenter. ID: ' + id + ', Nouveau nom: ' + nouveauNom);
        }
    }

    function supprimerDossier(id, nom) {
        if (confirm('Êtes-vous sûr de vouloir supprimer le dossier "' + nom + '" ?')) {
            // TODO: Implémenter la fonction de suppression
            alert('Fonction de suppression à implémenter. ID: ' + id);
        }
    }
</script>

</body>
</html>

<script>
    function renamerDossier(id, nomActuel) {
        const nouveauNom = prompt("Nouveau nom du dossier :", nomActuel);
        if (nouveauNom && nouveauNom.trim() !== '' && nouveauNom !== nomActuel) {
            const form = document.createElement("form");
            form.method = "post";
            form.action = window.location.href;

            const actionInput = document.createElement("input");
            actionInput.type = "hidden";
            actionInput.name = "action";
            actionInput.value = "renommer-dossier";

            const idInput = document.createElement("input");
            idInput.type = "hidden";
            idInput.name = "id";
            idInput.value = id;

            const nomInput = document.createElement("input");
            nomInput.type = "hidden";
            nomInput.name = "nom";
            nomInput.value = nouveauNom;

            form.appendChild(actionInput);
            form.appendChild(idInput);
            form.appendChild(nomInput);

            document.body.appendChild(form);
            form.submit();
        }
    }

    function supprimerDossier(id, nom) {
        if (confirm(`Supprimer le dossier "${nom}" ? Cette action est irréversible.`)) {
            const form = document.createElement("form");
            form.method = "post";
            form.action = window.location.href;

            const actionInput = document.createElement("input");
            actionInput.type = "hidden";
            actionInput.name = "action";
            actionInput.value = "supprimer-dossier";

            const idInput = document.createElement("input");
            idInput.type = "hidden";
            idInput.name = "id";
            idInput.value = id;

            form.appendChild(actionInput);
            form.appendChild(idInput);

            document.body.appendChild(form);
            form.submit();
        }
    }
</script>
