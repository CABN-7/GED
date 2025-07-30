package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.model.Document;
import com.groupeisi.ged.model.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/utilisateur/mes-documents")
public class MesDocumentsServlet extends HttpServlet {

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

        String titre = request.getParameter("titre");
        String statut = request.getParameter("statut");

        List<Document> documents = documentDao.findByUtilisateurWithFilters(user.getId(), statut, titre);


        request.setAttribute("documents", documents);
        request.getRequestDispatcher("/WEB-INF/pages/utilisateur/mes-documents.jsp").forward(request, response);
    }

}
