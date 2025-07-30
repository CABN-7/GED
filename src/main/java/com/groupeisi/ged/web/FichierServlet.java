package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.model.Document;
import com.groupeisi.ged.model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.Date;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,   // 1 Mo
        maxFileSize = 10 * 1024 * 1024,    // 10 Mo max
        maxRequestSize = 50 * 1024 * 1024  // 50 Mo max pour la requête
)
@WebServlet("/fichier")
public class FichierServlet extends HttpServlet {

    private static final String BASE_PATH = "/var/ged/uploads"; // à adapter selon ton système

    private static final String BASE_UPLOAD_DIR = System.getProperty("user.home") + File.separator + "ged_uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null || !user.isActif()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Accès non autorisé");
            return;
        }

        String nomFichier = request.getParameter("nom");
        if (nomFichier == null || nomFichier.contains("..")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nom de fichier invalide");
            return;
        }

        File fichier = new File(BASE_UPLOAD_DIR, nomFichier);
        if (!fichier.exists() || !fichier.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Fichier introuvable");
            return;
        }

        // Déduire le type MIME
        String mimeType = getServletContext().getMimeType(fichier.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fichier.getName() + "\"");
        response.setContentLengthLong(fichier.length());

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(fichier));
             BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        DocumentDao documentDao = new DocumentDao();

        if (user == null || !user.isActif()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Accès non autorisé");
            return;
        }

        // Vérifie que c’est bien un formulaire multipart
        if (!request.getContentType().startsWith("multipart/form-data")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Type de requête invalide.");
            return;
        }

        // Récupère le champ "fichier"
        Part part = request.getPart("fichier");
        String titre = request.getParameter("titre");

        if (part == null || part.getSize() == 0 || titre == null || titre.isEmpty()) {
            request.setAttribute("error", "Titre et fichier obligatoires !");
            request.getRequestDispatcher("/pages/utilisateur/upload.jsp").forward(request, response);
            return;
        }

        // Sécurise le nom du fichier
        String originalFileName = part.getSubmittedFileName();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String safeFileName = System.currentTimeMillis() + "_" + originalFileName;

        // Vérifie l’extension autorisée
        if (!List.of("pdf", "docx", "xlsx", "txt", "jpg", "png").contains(fileExtension.toLowerCase())) {
            request.setAttribute("error", "Format non autorisé !");
            request.getRequestDispatcher("/pages/utilisateur/upload.jsp").forward(request, response);
            return;
        }

        // Sauvegarde physique
        File uploadDir = new File(BASE_PATH);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String cheminFinal = BASE_PATH + File.separator + safeFileName;
        part.write(cheminFinal);

        // 🔁 Ici, tu peux créer et enregistrer une entité Document si tu veux (DAO)
        // Exemple :

        Document doc = new Document();
        doc.setTitre(titre);
        doc.setCheminFichier(safeFileName);
        doc.setProprietaire(user);
        doc.setDateCreation(new Date());
        doc.setStatut("EN_ATTENTE");
        documentDao.create(doc);


        // Message de succès
        request.setAttribute("success", "Fichier uploadé avec succès !");
        request.getRequestDispatcher("/pages/utilisateur/upload.jsp").forward(request, response);
    }

}
