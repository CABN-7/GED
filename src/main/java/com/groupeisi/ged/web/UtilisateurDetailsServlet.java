package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.UtilisateurDao;
import com.groupeisi.ged.model.Utilisateur;
import com.groupeisi.ged.model.Utilisateur.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/gestionnaire/utilisateur-details")
public class UtilisateurDetailsServlet extends HttpServlet {

    private UtilisateurDao utilisateurDao;

    @Override
    public void init() throws ServletException {
        utilisateurDao = new UtilisateurDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null || user.getRole() != Role.GESTIONNAIRE || !user.isActif()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID utilisateur manquant");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            Utilisateur utilisateur = utilisateurDao.findById(id);
            if (utilisateur == null) {
                request.setAttribute("errorMessage", "Utilisateur introuvable");
            }
            request.setAttribute("utilisateur", utilisateur);
            request.getRequestDispatcher("/WEB-INF/pages/gestionnaire/detail-utilisateur.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID utilisateur invalide");
        }
    }
}
