<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 30/07/2025
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% response.setCharacterEncoding("UTF-8"); %>
<% request.setCharacterEncoding("UTF-8"); %>

<li class="folder-block">
    <span class="toggle-button">▶</span>
    <span class="folder-name">📂 ${d.nom}</span>

    <!-- Actions -->
    <div style="display:inline; margin-left: 10px;">
        <a href="#" onclick="renamerDossier(${d.id}, '${d.nom}')" title="Renommer">✏️</a>
        <a href="#" onclick="supprimerDossier(${d.id}, '${d.nom}')" title="Supprimer">🗑️</a>
    </div>

    <!-- Formulaire de création de sous-dossier -->
    <form method="post" action="${pageContext.request.contextPath}/utilisateur/mes-dossiers"
          style="display:inline; margin-left: 10px;">
        <input type="hidden" name="action" value="creer-dossier">
        <input type="hidden" name="parentId" value="${d.id}">
        <input type="text" name="nom" placeholder="Nouveau dossier" required style="width: 120px;">
        <button type="submit">➕</button>
    </form>

    <!-- Documents du dossier -->
    <c:if test="${not empty d.documents}">
        <ul class="hidden">
            <c:forEach var="doc" items="${d.documents}">
                <li class="document">
                    📄 ${doc.titre} [${doc.statut}]
                    - <a href="${pageContext.request.contextPath}/fichier?nom=${doc.cheminFichier}"
                         target="_blank" title="Télécharger">📥</a>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <!-- Sous-dossiers récursifs -->
    <c:if test="${not empty d.sousDossiers}">
        <ul class="hidden">
            <c:forEach var="d" items="${d.sousDossiers}">
                <jsp:include page="/WEB-INF/fragments/dossier-item.jsp">
                    <jsp:param name="d" value="${d}"/>
                </jsp:include>
            </c:forEach>
        </ul>
    </c:if>
</li>

