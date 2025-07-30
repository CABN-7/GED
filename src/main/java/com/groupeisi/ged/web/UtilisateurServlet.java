package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.UtilisateurDao;
import com.groupeisi.ged.model.Utilisateur;
import com.groupeisi.ged.utils.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/utilisateurs")
public class UtilisateurServlet extends HttpServlet {

    private UtilisateurDao utilisateurDao;

    @Override
    public void init() throws ServletException {
        utilisateurDao = new UtilisateurDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Utilisateur> utilisateurs = utilisateurDao.findAll();
        request.setAttribute("utilisateurs", utilisateurs);
        // Forward vers JSP pour affichage
        request.getRequestDispatcher("/WEB-INF/utilisateurs.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        Utilisateur.Role role = Utilisateur.Role.valueOf(request.getParameter("role").toUpperCase()); // Nouveau champ

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(PasswordUtil.hashPassword(motDePasse));
        utilisateur.setRole(role); // Affectation du rôle

        utilisateurDao.create(utilisateur);

        response.sendRedirect(request.getContextPath() + "/utilisateurs");
    }

}
