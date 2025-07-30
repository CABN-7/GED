package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DossierDao;
import com.groupeisi.ged.model.Dossier;
import com.groupeisi.ged.utils.PasswordUtil;

import com.groupeisi.ged.dao.UtilisateurDao;
import com.groupeisi.ged.model.Utilisateur;
import com.groupeisi.ged.model.Utilisateur.Role;
import com.groupeisi.ged.utils.EmailUtil;
import com.groupeisi.ged.utils.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

import static com.groupeisi.ged.model.Utilisateur.Role.*;

@WebServlet("/utilisateur/creer-sous-dossier")
public class CreerSousDossierServlet extends HttpServlet {

    private DossierDao dossierDao;

    @Override
    public void init() {
        dossierDao = new DossierDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String nom = request.getParameter("nom");
        String parentIdStr = request.getParameter("parentId");

        if (nom == null || nom.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/utilisateur/mes-dossiers?error=Nom requis");
            return;
        }

        Dossier sousDossier = new Dossier(nom);
        if (parentIdStr != null) {
            Long parentId = Long.parseLong(parentIdStr);
            Dossier parent = dossierDao.findById(parentId);
            sousDossier.setParent(parent);
        }

        dossierDao.create(sousDossier);
        response.sendRedirect(request.getContextPath() + "/utilisateur/mes-dossiers");
    }
}
