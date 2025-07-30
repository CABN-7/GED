<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 29/07/2025
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.user}">
    <nav style="background-color:#2c3e50; padding: 10px;">
        <div style="max-width: 1200px; margin: 0 auto; display: flex; align-items: center; justify-content: space-between;">

            <!-- Titre -->
            <div>
                <span style="color: #ecf0f1; font-weight: bold; font-size: 1.2em;">
                    GED - ${sessionScope.user.role}
                </span>
            </div>

            <!-- Navigation -->
            <div style="display: flex; align-items: center; gap: 20px;">

                <ul style="list-style: none; margin: 0; padding: 0; display: flex; gap: 20px;">
                    <c:choose>
                        <%-- GESTIONNAIRE --%>
                        <c:when test="${sessionScope.user.role == 'GESTIONNAIRE'}">
                            <li><a href="${pageContext.request.contextPath}/gestionnaire/dashboard"
                                   style="color: #ecf0f1; text-decoration: none;">Dashboard</a></li>
                            <li><a href="${pageContext.request.contextPath}/gestionnaire/documents"
                                   style="color: #ecf0f1; text-decoration: none;">Documents</a></li>
                            <li><a href="${pageContext.request.contextPath}/gestionnaire/utilisateurs"
                                   style="color: #ecf0f1; text-decoration: none;">Utilisateurs</a></li>
                        </c:when>

                        <%-- UTILISATEUR --%>
                        <c:when test="${sessionScope.user.role == 'UTILISATEUR'}">
                            <li><a href="${pageContext.request.contextPath}/utilisateur/dashboard"
                                   style="color: #ecf0f1; text-decoration: none;">Accueil</a></li>
                            <li><a href="${pageContext.request.contextPath}/utilisateur/mes-documents"
                                   style="color: #ecf0f1; text-decoration: none;">Mes documents</a></li>
                            <li><a href="${pageContext.request.contextPath}/utilisateur/mes-dossiers"
                                   style="color: #ecf0f1; text-decoration: none;">Mes dossiers</a></li>
                            <li><a href="${pageContext.request.contextPath}/utilisateur/depot-document"
                                   style="color: #ecf0f1; text-decoration: none;">Déposer</a></li>
                        </c:when>


                        <%-- ADMIN --%>
                        <c:when test="${sessionScope.user.role == 'ADMIN'}">
                            <li><a href="${pageContext.request.contextPath}/admin/utilisateurs"
                                   style="color: #ecf0f1; text-decoration: none;">Utilisateurs</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/statistiques"
                                   style="color: #ecf0f1; text-decoration: none;">Statistiques</a></li>
                        </c:when>
                    </c:choose>

                    <!-- Déconnexion -->
                    <li>
                        <a href="${pageContext.request.contextPath}/logout"
                           style="color: #ecf0f1; text-decoration: none;">Déconnexion</a>
                    </li>
                </ul>

                <!-- Nom utilisateur -->
                <span style="color: #ecf0f1; font-size: 0.9em;">
                    Bonjour, ${sessionScope.user.prenom} ${sessionScope.user.nom}
                </span>
            </div>
        </div>
    </nav>
</c:if>
