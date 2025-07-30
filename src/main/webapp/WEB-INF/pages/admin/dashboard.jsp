<%--
  Created by IntelliJ IDEA.
  User: cabnc
  Date: 26/07/2025
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.groupeisi.ged.model.Utilisateur" %>
<%
    Utilisateur u = (Utilisateur) session.getAttribute("utilisateurConnecte");
    if (u == null || u.getRole() != Utilisateur.Role.ADMIN) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
%>
<h1>Bienvenue Admin <%= u.getPrenom() %></h1>

