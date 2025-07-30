package com.groupeisi.ged.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // Ne crée pas de session si elle n'existe pas
        if (session != null) {
            session.invalidate(); // Supprime toutes les données de session
        }

        response.sendRedirect(request.getContextPath() + "/pages/login.jsp"); // Redirection vers la page de connexion
    }
}
