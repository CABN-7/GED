package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.model.Document;
import com.groupeisi.ged.model.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/gestionnaire/traiter-document")
public class TraiterDocumentServlet extends HttpServlet {

    private DocumentDao documentDao;

    @Override
    public void init() throws ServletException {
        documentDao = new DocumentDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        // Vérifie l'accès
        if (user == null || user.getRole() != Utilisateur.Role.GESTIONNAIRE || !user.isActif()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit.");
            return;
        }

        try {
            Long docId = Long.parseLong(request.getParameter("id"));
            String action = request.getParameter("action");

            if (!action.equals("valider") && !action.equals("rejeter")) {
                request.setAttribute("error", "Action invalide.");
                response.sendRedirect(request.getContextPath() + "/gestionnaire/dashboard");
                return;
            }

            String nouveauStatut = action.equals("valider") ? "VALIDÉ" : "REJETÉ";

            documentDao.updateStatut(docId, nouveauStatut);

            // ✅ Message de confirmation (optionnel, si affiché dans la JSP)
            session.setAttribute("successMessage", "Document " + (action.equals("valider") ? "validé" : "rejeté") + " avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Erreur lors du traitement du document.");
        }

        // Redirection vers le dashboard
        response.sendRedirect(request.getContextPath() + "/gestionnaire/dashboard");
    }
}
