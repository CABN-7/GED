package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.model.Document;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/utilisateur/document-details")
public class DocumentDetailsUtilisateurServlet extends HttpServlet {

    private DocumentDao documentDao;

    @Override
    public void init() {
        documentDao = new DocumentDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        Document doc = documentDao.findById(id);

        HttpSession session = request.getSession();
        if (doc == null || !doc.getProprietaire().getId().equals(((com.groupeisi.ged.model.Utilisateur) session.getAttribute("user")).getId())) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Document introuvable");
            return;
        }

        request.setAttribute("document", doc);
        request.getRequestDispatcher("/WEB-INF/pages/utilisateur/document-details.jsp").forward(request, response);
    }
}
