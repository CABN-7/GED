<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 30/07/2025
  Time: 02:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="dossier" value="${dossier}" scope="request"/>
<c:set var="level" value="${param.level}"/>

<li class="folder-block">
    <span class="toggle-button">▶</span>
    <span class="folder-name">📂 ${dossier.nom}</span>
    <form method="post" action="${pageContext.request.contextPath}/utilisateur/creer-sous-dossier"
          style="display:inline;">
        <input type="hidden" name="parentId" value="${dossier.id}">
        <input type="text" name="nom" placeholder="Sous-dossier" required>
        <button type="submit">➕</button>
    </form>

    <c:if test="${not empty dossier.documents}">
        <ul>
            <c:forEach var="doc" items="${dossier.documents}">
                <li class="document">
                    📄 ${doc.titre} [${doc.statut}]
                    - <a href="${pageContext.request.contextPath}/fichier?nom=${doc.cheminFichier}"
                         target="_blank">📥</a>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${not empty dossier.sousDossiers}">
        <ul class="hidden">
            <c:forEach var="child" items="${dossier.sousDossiers}">
                <jsp:include page="/WEB-INF/fragments/dossier-recursif.jsp">
                    <jsp:param name="dossierId" value="${child.id}"/>
                    <jsp:param name="level" value="${level + 1}"/>
                </jsp:include>
            </c:forEach>
        </ul>
    </c:if>
</li>

