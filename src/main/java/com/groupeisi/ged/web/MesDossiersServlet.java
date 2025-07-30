package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DossierDao;
import com.groupeisi.ged.model.Dossier;
import com.groupeisi.ged.model.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

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

        try {
            // S'assurer que l'utilisateur a un dossier racine
            Dossier racine = dossierDao.findOrCreateRacineForUser(user);

            // Récupérer le dossier racine avec toute sa hiérarchie
            Dossier dossierRacineComplet = dossierDao.getRootFolderWithHierarchy(user.getId());

            // Passer le dossier racine à la JSP
            request.setAttribute("dossierRacine", dossierRacineComplet);
            request.getRequestDispatcher("/WEB-INF/pages/utilisateur/mes-dossiers.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du chargement des dossiers : " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/utilisateur/mes-dossiers.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
        if (user == null || !user.isActif()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "creer-dossier":
                    //String nom = request.getParameter("nom");
                    String nomDossier = request.getParameter("nom");
                    String parentIdStr = request.getParameter("parentId");
                    try {
                        if (nomDossier != null && !nomDossier.trim().isEmpty()) {
                            Long parentId;
                            if (parentIdStr != null && !parentIdStr.isEmpty()) {
                                parentId = Long.parseLong(parentIdStr);
                            } else {
                                // Si pas de parentId, on met le dossier racine comme parent
                                Dossier racine = dossierDao.findOrCreateRacineForUser(user);
                                parentId = racine.getId();
                            }
                            dossierDao.createSousDossier(nomDossier.trim(), user.getId(), parentId);
                            request.setAttribute("success", "Dossier créé avec succès !");
                        } else {
                            request.setAttribute("error", "Le nom du dossier ne peut pas être vide.");
                        }
                    } catch (Exception e) {
                        request.setAttribute("error", "Erreur lors de la création du dossier : " + e.getMessage());
                    }
                    break;

                case "renommer-dossier":
                    Long idRenom = Long.parseLong(request.getParameter("id"));
                    String nouveauNom = request.getParameter("nom");
                    Dossier dossierRenom = dossierDao.findById(idRenom);
                    if (dossierRenom != null && !dossierRenom.isRacine()) {
                        dossierRenom.setNom(nouveauNom);
                        dossierDao.update(dossierRenom);
                    }
                    break;

                case "supprimer-dossier":
                    Long idSupp = Long.parseLong(request.getParameter("id"));
                    dossierDao.delete(idSupp);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur : " + e.getMessage());
        }

        // 🔁 Redirection pour éviter re-soumission
        response.sendRedirect(request.getContextPath() + "/utilisateur/mes-dossiers");
    }

}