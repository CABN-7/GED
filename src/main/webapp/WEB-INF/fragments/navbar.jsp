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
    <style>
        .navbar {
            background-color: #2c3e50;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .navbar-brand {
            color: #ecf0f1;
            font-size: 1.4em;
            font-weight: bold;
            text-transform: uppercase;
        }

        .navbar-menu {
            display: flex;
            gap: 20px;
            align-items: center;
        }

        .navbar-menu a {
            color: #ecf0f1;
            text-decoration: none;
            font-weight: 500;
            padding: 6px 10px;
            border-radius: 4px;
            transition: background-color 0.3s;
        }

        .navbar-menu a:hover {
            background-color: #3498db;
        }

        .navbar-user {
            color: #bdc3c7;
            font-size: 0.95em;
            margin-left: 15px;
            font-style: italic;
        }

        @media screen and (max-width: 768px) {
            .navbar {
                flex-direction: column;
                align-items: flex-start;
            }

            .navbar-menu {
                flex-direction: column;
                align-items: flex-start;
                gap: 10px;
                margin-top: 10px;
            }
        }
    </style>

    <nav class="navbar">
        <div class="navbar-brand">
            GED - ${sessionScope.user.role}
        </div>

        <div class="navbar-menu">
            <c:choose>
                <%-- GESTIONNAIRE --%>
                <c:when test="${sessionScope.user.role == 'GESTIONNAIRE'}">
                    <a href="${pageContext.request.contextPath}/gestionnaire/dashboard">Dashboard</a>
                    <a href="${pageContext.request.contextPath}/gestionnaire/documents">Documents</a>
                    <a href="${pageContext.request.contextPath}/gestionnaire/utilisateurs">Utilisateurs</a>
                </c:when>

                <%-- UTILISATEUR --%>
                <c:when test="${sessionScope.user.role == 'UTILISATEUR'}">
                    <a href="${pageContext.request.contextPath}/utilisateur/dashboard">Accueil</a>
                    <a href="${pageContext.request.contextPath}/utilisateur/mes-documents">Mes documents</a>
                    <a href="${pageContext.request.contextPath}/utilisateur/mes-dossiers">Mes dossiers</a>
                    <a href="${pageContext.request.contextPath}/utilisateur/depot-document">Déposer</a>
                </c:when>

                <%-- ADMIN --%>
                <c:when test="${sessionScope.user.role == 'ADMIN'}">
                </c:when>
            </c:choose>

            <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
            <span class="navbar-user">Bonjour, ${sessionScope.user.prenom} ${sessionScope.user.nom}</span>
        </div>
    </nav>
</c:if>
