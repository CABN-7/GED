<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 30/07/2025
  Time: 13:20
  To change this template use File | Settings | File Templates.
--%>
<%--
  Fragment pour affichage du dossier racine et ses sous-dossiers
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% response.setCharacterEncoding("UTF-8"); %>
<% request.setCharacterEncoding("UTF-8"); %>

<li class="folder-block racine-folder">
    <span class="toggle-button">▼</span>
    <span class="folder-name">📁 ${dossier.nom}</span>

    <!-- Formulaire de création de sous-dossier -->
    <form method="post" action="${pageContext.request.contextPath}/utilisateur/mes-dossiers"
          style="display:inline; margin-left: 10px;">
        <input type="hidden" name="action" value="creer-dossier">
        <input type="hidden" name="parentId" value="${dossier.id}">
        <input type="text" name="nom" placeholder="Nouveau dossier" required style="width: 150px;">
        <button type="submit">➕ Créer</button>
    </form>


    <ul class="sous-dossiers">
        <!-- Affichage des documents de la racine -->
        <c:if test="${not empty dossier.documents}">
            <c:forEach var="doc" items="${dossier.documents}">
                <li class="document">
                    📄 ${doc.titre} [${doc.statut}]
                    - <a href="${pageContext.request.contextPath}/fichier?nom=${doc.cheminFichier}"
                         target="_blank" title="Télécharger">📥</a>
                </li>
            </c:forEach>
        </c:if>

        <!-- Affichage des sous-dossiers -->
        <c:if test="${not empty dossier.sousDossiers}">
            <c:forEach var="sousDossier" items="${dossier.sousDossiers}">
                <li class="folder-block sous-dossier">
                    <span class="toggle-button">▶</span>
                    <span class="folder-name">📂 ${sousDossier.nom}</span>

                    <!-- Actions sur le sous-dossier -->
                    <div style="display:inline; margin-left: 10px;">
                        <a href="#" onclick="renamerDossier(${sousDossier.id}, '${sousDossier.nom}')" title="Renommer">✏️</a>
                        <a href="#" onclick="supprimerDossier(${sousDossier.id}, '${sousDossier.nom}')"
                           title="Supprimer">🗑️</a>
                    </div>

                    <!-- Formulaire pour créer un sous-sous-dossier -->
                    <form method="post" action="${pageContext.request.contextPath}/utilisateur/mes-dossiers"
                          style="display:inline; margin-left: 10px;">
                        <input type="hidden" name="action" value="creer-dossier">
                        <input type="hidden" name="parentId" value="${sousDossier.id}">
                        <input type="text" name="nom" placeholder="Nouveau dossier" required style="width: 120px;">
                        <button type="submit">➕</button>
                    </form>

                    <!-- Documents du sous-dossier -->
                    <c:if test="${not empty sousDossier.documents}">
                        <ul class="hidden">
                            <c:forEach var="doc" items="${sousDossier.documents}">
                                <li class="document">
                                    📄 ${doc.titre} [${doc.statut}]
                                    - <a href="${pageContext.request.contextPath}/fichier?nom=${doc.cheminFichier}"
                                         target="_blank" title="Télécharger">📥</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>

                    <!-- Sous-dossiers de niveau 2 (si nécessaire) -->
                    <c:if test="${not empty sousDossier.sousDossiers}">
                        <ul class="hidden">
                            <c:forEach var="sousSousDossier" items="${sousDossier.sousDossiers}">
                                <li class="folder-block">
                                    <span class="folder-name">📂 ${sousSousDossier.nom}</span>
                                    <!-- Ajouter plus de niveaux si nécessaire -->
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </li>
            </c:forEach>
        </c:if>

        <!-- Message si aucun contenu -->
        <c:if test="${empty dossier.documents and empty dossier.sousDossiers}">
            <li class="empty-folder">
                <em>Aucun fichier ou dossier</em>
            </li>
        </c:if>
    </ul>
</li>
