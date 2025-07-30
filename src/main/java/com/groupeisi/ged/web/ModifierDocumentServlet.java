package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.dao.DossierDao;
import com.groupeisi.ged.dao.TypeDocumentDao;
import com.groupeisi.ged.model.Document;
import com.groupeisi.ged.model.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/utilisateur/modifier-document")
public class ModifierDocumentServlet extends HttpServlet {

    private DocumentDao documentDao;
    private TypeDocumentDao typeDocumentDao;
    private DossierDao dossierDao;

    @Override
    public void init() {
        documentDao = new DocumentDao();
        typeDocumentDao = new TypeDocumentDao();
        dossierDao = new DossierDao();
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

        Long id = Long.parseLong(request.getParameter("id"));
        Document document = documentDao.findById(id);

        if (document == null || !document.getProprietaire().getId().equals(user.getId()) ||
                !document.getStatut().equals("EN_ATTENTE")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Modification non autorisée");
            return;
        }

        request.setAttribute("document", document);
        request.setAttribute("types", typeDocumentDao.findAll());
        request.setAttribute("dossiers", dossierDao.findAll());
        request.getRequestDispatcher("/WEB-INF/pages/utilisateur/modifier-document.jsp").forward(request, response);
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
        String titre = request.getParameter("titre");
        Long typeId = Long.parseLong(request.getParameter("type"));
        Long dossierId = Long.parseLong(request.getParameter("dossier"));

        Document document = documentDao.findById(id);

        if (document == null || !document.getProprietaire().getId().equals(user.getId()) ||
                !document.getStatut().equals("EN_ATTENTE")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Modification non autorisée");
            return;
        }

        document.setTitre(titre);
        document.setType(typeDocumentDao.findById(typeId));
        document.setDossier(dossierDao.findById(dossierId));

        documentDao.update(document);

        response.sendRedirect(request.getContextPath() + "/utilisateur/mes-documents");
    }
}
