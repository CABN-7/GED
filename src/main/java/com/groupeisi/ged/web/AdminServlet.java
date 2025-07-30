package com.groupeisi.ged.web;

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

@WebServlet("/admin/utilisateurs")
public class AdminServlet extends HttpServlet {

    private UtilisateurDao utilisateurDao;
    private static final int PAGE_SIZE = 10;

    @Override
    public void init() throws ServletException {
        utilisateurDao = new UtilisateurDao();
    }

    private boolean isAdmin(HttpSession session) {
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        return user != null && user.getRole() == Role.ADMIN && user.isActif();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (!isAdmin(session)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé.");
            return;
        }

        Utilisateur adminConnecte = (Utilisateur) session.getAttribute("user");

        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException ignored) {
            }
        }

        // Récupération brute
        List<Utilisateur> utilisateurs = utilisateurDao.findAllPaged(page, PAGE_SIZE);

        // ❌ Filtrer pour exclure l'admin connecté
        utilisateurs.removeIf(u -> u.getId().equals(adminConnecte.getId()));

        // Recalculer éventuellement le nombre réel si tu veux ajuster la pagination
        long totalUsers = utilisateurDao.countAll() - 1; // -1 car on exclut l'admin connecté
        int totalPages = (int) Math.ceil((double) totalUsers / PAGE_SIZE);

        // Statistiques
        long totalAdmins = utilisateurDao.countUsersByRole(Role.ADMIN);
        long totalGestionnaires = utilisateurDao.countUsersByRole(Role.GESTIONNAIRE);
        long totalUtilisateurs = utilisateurDao.countUsersByRole(Role.UTILISATEUR);

        // Injection des données
        request.setAttribute("utilisateurs", utilisateurs);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalAdmins", totalAdmins);
        request.setAttribute("totalGestionnaires", totalGestionnaires);
        request.setAttribute("totalUtilisateurs", totalUtilisateurs);

        request.getRequestDispatcher("/WEB-INF/pages/admin/admin_dashboard.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request.getSession())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé.");
            return;
        }

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            if ("creer".equals(action)) {
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String email = request.getParameter("email");
                String motDePasse = request.getParameter("motDePasse");

                if (nom == null || prenom == null || email == null || motDePasse == null
                        || nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
                    session.setAttribute("errorMessage", "Tous les champs sont obligatoires.");
                    response.sendRedirect(request.getContextPath() + "/admin/utilisateurs");
                    return;
                }

                if (utilisateurDao.findByEmail(email) != null) {
                    session.setAttribute("errorMessage", "Cet email est déjà utilisé.");
                    response.sendRedirect(request.getContextPath() + "/admin/utilisateurs");
                    return;
                }

                String hashedPwd = PasswordUtil.hashPassword(motDePasse);

                Utilisateur nouveauGestionnaire = new Utilisateur();
                nouveauGestionnaire.setNom(nom);
                nouveauGestionnaire.setPrenom(prenom);
                nouveauGestionnaire.setEmail(email);
                nouveauGestionnaire.setMotDePasse(hashedPwd);
                nouveauGestionnaire.setRole(Role.GESTIONNAIRE);
                nouveauGestionnaire.setActif(true);
                nouveauGestionnaire.setDoitChangerMotDePasse(true);

                utilisateurDao.create(nouveauGestionnaire);
                // ✅ Envoi de l'email
                String sujet = "Création de votre compte GED";
                String message = String.format("""
                        Bonjour %s %s,
                        
                        Votre compte de gestionnaire a été créé avec succès.
                        
                        Identifiants de connexion :
                        - Email : %s
                        - Mot de passe temporaire : %s
                        
                        ⚠️ Veuillez changer votre mot de passe lors de votre première connexion.
                        
                        Lien vers la plateforme : %s/login
                        
                        Cordialement,
                        L’équipe GED
                        """, nom, prenom, email, motDePasse, request.getRequestURL().toString().replace("/admin/utilisateurs", ""));

                try {
                    EmailUtil.envoyer(email, sujet, message);
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("errorMessage", "Gestionnaire créé, mais l'e-mail n’a pas pu être envoyé.");
                }
                session.setAttribute("successMessage", "Gestionnaire créé avec succès.");

            } else if ("activer".equals(action) || "desactiver".equals(action)) {
                Long userId = Long.parseLong(request.getParameter("userId"));
                boolean actif = "activer".equals(action);
                utilisateurDao.updateActif(userId, actif);
                session.setAttribute("successMessage", "Utilisateur " + (actif ? "activé" : "désactivé") + ".");

            } else if ("supprimer".equals(action)) {
                Long userId = Long.parseLong(request.getParameter("userId"));
                utilisateurDao.delete(userId);
                session.setAttribute("successMessage", "Utilisateur supprimé.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Une erreur est survenue: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/utilisateurs");
    }
}
