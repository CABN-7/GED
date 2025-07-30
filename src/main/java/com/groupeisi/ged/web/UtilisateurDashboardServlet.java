package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.model.Document;
import com.groupeisi.ged.model.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/utilisateur/dashboard")
public class UtilisateurDashboardServlet extends HttpServlet {

    private DocumentDao documentDao;

    @Override
    public void init() {
        documentDao = new DocumentDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null || user.getRole() != Utilisateur.Role.UTILISATEUR || !user.isActif()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit");
            return;
        }

        List<Document> documents = documentDao.findByProprietaireId(user.getId());

        long total = documents.size();
        long valides = documents.stream().filter(d -> d.getStatut().equals("VALIDÉ")).count();
        long rejetes = documents.stream().filter(d -> d.getStatut().equals("REJETÉ")).count();
        long attente = documents.stream().filter(d -> d.getStatut().equals("EN_ATTENTE")).count();

        request.setAttribute("documents", documents);
        request.setAttribute("total", total);
        request.setAttribute("valides", valides);
        request.setAttribute("rejetes", rejetes);
        request.setAttribute("attente", attente);

        request.getRequestDispatcher("/WEB-INF/pages/utilisateur/dashboard.jsp").forward(request, response);
    }
}
