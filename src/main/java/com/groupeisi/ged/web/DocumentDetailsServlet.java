package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.model.Document;
import com.groupeisi.ged.model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/gestionnaire/document-details")
public class DocumentDetailsServlet extends HttpServlet {

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

        if (user == null || user.getRole() != Utilisateur.Role.GESTIONNAIRE || !user.isActif()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit.");
            return;
        }

        Long docId;
        try {
            docId = Long.parseLong(request.getParameter("id"));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID invalide.");
            return;
        }

        Document document = documentDao.findById(docId);
        if (document == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Document introuvable.");
            return;
        }

        request.setAttribute("document", document);
        request.getRequestDispatcher("/WEB-INF/pages/gestionnaire/document-details.jsp").forward(request, response);
    }
}
