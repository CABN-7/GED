package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.model.Document;
import com.groupeisi.ged.model.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/gestionnaire/documents")
public class GestionnaireDocumentsServlet extends HttpServlet {

    private DocumentDao documentDao;

    @Override
    public void init() {
        documentDao = new DocumentDao();
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

        List<Document> documents = documentDao.findAll();

        request.setAttribute("documents", documents);
        request.getRequestDispatcher("/WEB-INF/pages/gestionnaire/documents.jsp").forward(request, response);
    }
}
