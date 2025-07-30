package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.UtilisateurDao;
import com.groupeisi.ged.model.Utilisateur;
import com.groupeisi.ged.utils.EmailUtil;
import com.groupeisi.ged.utils.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Date;

@WebServlet("/gestionnaire/ajouter-utilisateur")
public class AjoutUtilisateurServlet extends HttpServlet {

    private UtilisateurDao utilisateurDao;

    @Override
    public void init() {
        utilisateurDao = new UtilisateurDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Affiche le formulaire
        request.getRequestDispatcher("/WEB-INF/pages/gestionnaire/ajouter-utilisateur.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        if (nom == null || prenom == null || email == null || motDePasse == null ||
                nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            request.setAttribute("error", "Tous les champs sont obligatoires.");
            request.getRequestDispatcher("/WEB-INF/pages/gestionnaire/ajouter-utilisateur.jsp").forward(request, response);
            return;
        }

        // Création de l’utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(PasswordUtil.hashPassword(motDePasse));
        utilisateur.setActif(true);
        utilisateur.setDoitChangerMotDePasse(true);
        utilisateur.setDateCreation(new Date());
        utilisateur.setRole(Utilisateur.Role.UTILISATEUR);

        utilisateurDao.create(utilisateur);

        // Envoyer un email de bienvenue
        try {
            String message = """
                    Bonjour %s %s,
                    
                    Votre compte utilisateur GED a été créé avec succès.
                    
                    Vos identifiants :
                    Email : %s
                    Mot de passe temporaire : %s
                    
                    Veuillez vous connecter et modifier votre mot de passe immédiatement.
                    
                    L’équipe GED.
                    """.formatted(prenom, nom, email, motDePasse);

            EmailUtil.envoyer(email, "Création de votre compte GED", message);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du mail : " + e.getMessage());
        }


        request.setAttribute("success", "Utilisateur ajouté avec succès !");
        request.getRequestDispatcher("/WEB-INF/pages/gestionnaire/ajouter-utilisateur.jsp").forward(request, response);
    }
}
