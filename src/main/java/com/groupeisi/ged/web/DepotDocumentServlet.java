package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DossierDao;
import com.groupeisi.ged.dao.TypeDocumentDao;
import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@WebServlet("/utilisateur/depot-document")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5,   // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class DepotDocumentServlet extends HttpServlet {

    private static final String BASE_UPLOAD_DIR = System.getProperty("user.home") + File.separator + "ged_uploads";

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

        List<TypeDocument> types = typeDocumentDao.findAll();
        List<Dossier> dossiers = dossierDao.findAll();

        request.setAttribute("types", types);
        request.setAttribute("dossiers", dossiers);

        request.getRequestDispatcher("/WEB-INF/pages/utilisateur/depot-document.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        String titre = request.getParameter("titre");
        String dossierParam = request.getParameter("dossier");
        String nouveauDossierNom = request.getParameter("nouveauDossier");
        String typeParam = request.getParameter("type");
        Part fichierPart = request.getPart("fichier");

        if (titre == null || titre.isEmpty() || fichierPart == null || fichierPart.getSize() == 0
                || typeParam == null || typeParam.isEmpty()) {
            request.setAttribute("error", "Tous les champs sont obligatoires.");
            doGet(request, response);
            return;
        }

        Long typeId;
        try {
            typeId = Long.parseLong(typeParam);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Type de document invalide.");
            doGet(request, response);
            return;
        }

        // 🔁 Traitement du dossier
        Dossier dossier = null;
        try {
            if ("racine".equals(dossierParam)) {
                dossier = dossierDao.findOrCreateByName("Racine");
            } else if ("nouveau".equals(dossierParam)) {
                if (nouveauDossierNom != null && !nouveauDossierNom.trim().isEmpty()) {
                    dossier = new Dossier(nouveauDossierNom.trim());
                    dossierDao.create(dossier);
                } else {
                    request.setAttribute("error", "Le nom du nouveau dossier est requis.");
                    doGet(request, response);
                    return;
                }
            } else {
                Long dossierId = Long.parseLong(dossierParam);
                dossier = dossierDao.findById(dossierId);
                if (dossier == null) {
                    dossier = dossierDao.findOrCreateByName("Racine");
                }
            }
        } catch (Exception e) {
            dossier = dossierDao.findOrCreateByName("Racine");
        }

        // 📁 Créer le dossier de stockage si nécessaire
        File uploadDir = new File(BASE_UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 🔐 Nom sécurisé
        String nomFichier = Paths.get(fichierPart.getSubmittedFileName()).getFileName().toString();
        String nomFinal = System.currentTimeMillis() + "_" + nomFichier;

        String cheminFichier = BASE_UPLOAD_DIR + File.separator + nomFinal;
        fichierPart.write(cheminFichier);

        // 💾 Création du document
        Document doc = new Document();
        doc.setTitre(titre);
        doc.setCheminFichier(nomFinal); // stocker que le nom dans la BDD
        doc.setStatut("EN_ATTENTE");
        doc.setDateCreation(new Date());
        doc.setProprietaire(user);
        doc.setType(typeDocumentDao.findById(typeId));
        doc.setDossier(dossier);

        documentDao.create(doc);

        response.sendRedirect(request.getContextPath() + "/utilisateur/dashboard");
    }
}
