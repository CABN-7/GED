package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.UtilisateurDao;
import com.groupeisi.ged.model.Utilisateur;
import com.groupeisi.ged.utils.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UtilisateurDao utilisateurDao;

    @Override
    public void init() {
        utilisateurDao = new UtilisateurDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Utilisateur utilisateur = utilisateurDao.findByEmail(email);

        if (utilisateur != null &&
                PasswordUtil.hashPassword(motDePasse).equals(utilisateur.getMotDePasse())) { // ou checkPassword si tu l'implémentes

            HttpSession session = request.getSession();
            session.setAttribute("user", utilisateur); // clé utilisée dans AdminServlet

            if (utilisateur.isDoitChangerMotDePasse()) {
                // Redirige vers page de changement de mot de passe
                response.sendRedirect(request.getContextPath() + "/changer-mot-de-passe.jsp");
                return;
            }
            if (!utilisateur.isActif()) {
                request.setAttribute("error", "Votre compte est inactif. Veuillez contacter l'administrateur.");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
                return;
            }
            switch (utilisateur.getRole()) {
                case ADMIN:
                    response.sendRedirect(request.getContextPath() + "/admin/utilisateurs");
                    break;
                case GESTIONNAIRE:
                    response.sendRedirect(request.getContextPath() + "/gestionnaire/dashboard");
                    break;
                case UTILISATEUR:
                    response.sendRedirect(request.getContextPath() + "/utilisateur/dashboard");
                    break;
            }
        } else {
            request.setAttribute("error", "Identifiants invalides !");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }
}
