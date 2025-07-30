package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.UtilisateurDao;
import com.groupeisi.ged.model.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/gestionnaire/utilisateurs")
public class GestionnaireUtilisateursServlet extends HttpServlet {

    private UtilisateurDao utilisateurDao;

    @Override
    public void init() {
        utilisateurDao = new UtilisateurDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null || user.getRole() != Utilisateur.Role.GESTIONNAIRE || !user.isActif()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès non autorisé");
            return;
        }

        List<Utilisateur> utilisateurs = utilisateurDao.findAllByRole(Utilisateur.Role.UTILISATEUR);

        request.setAttribute("utilisateurs", utilisateurs);
        request.getRequestDispatcher("/WEB-INF/pages/gestionnaire/utilisateurs.jsp").forward(request, response);
    }
}
