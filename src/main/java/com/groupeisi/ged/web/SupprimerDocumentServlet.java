package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.model.Document;
import com.groupeisi.ged.model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/utilisateur/supprimer-document")
public class SupprimerDocumentServlet extends HttpServlet {

    private DocumentDao documentDao;

    @Override
    public void init() {
        documentDao = new DocumentDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null || user.getRole() != Utilisateur.Role.UTILISATEUR || !user.isActif()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit");
            return;
        }

        Long id = Long.parseLong(request.getParameter("id"));
        Document doc = documentDao.findById(id);

        if (doc != null && doc.getProprietaire().getId().equals(user.getId())) {
            documentDao.delete(id);
        }

        response.sendRedirect(request.getContextPath() + "/utilisateur/mes-documents");
    }
}
