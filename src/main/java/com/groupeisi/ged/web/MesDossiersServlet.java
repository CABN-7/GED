package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.dao.DossierDao;
import com.groupeisi.ged.model.Document;
import com.groupeisi.ged.model.Dossier;
import com.groupeisi.ged.model.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/utilisateur/mes-dossiers")
public class MesDossiersServlet extends HttpServlet {

    private DossierDao dossierDao;

    @Override
    public void init() {
        dossierDao = new DossierDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
        if (user == null || !user.isActif()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        List<Dossier> dossiers = dossierDao.findAllParentsWithChildrenAndDocuments(user.getId());
        request.setAttribute("dossiers", dossiers);
        request.getRequestDispatcher("/WEB-INF/pages/utilisateur/mes-dossiers.jsp").forward(request, response);
    }
}
