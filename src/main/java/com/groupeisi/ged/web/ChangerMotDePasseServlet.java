package com.groupeisi.ged.web;

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

@WebServlet("/changer-mot-de-passe")
public class ChangerMotDePasseServlet extends HttpServlet {

    private UtilisateurDao utilisateurDao;

    @Override
    public void init() {
        utilisateurDao = new UtilisateurDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String nouveau = request.getParameter("nouveauMotDePasse");
        String confirmation = request.getParameter("confirmation");

        if (nouveau == null || confirmation == null || !nouveau.equals(confirmation)) {
            request.setAttribute("error", "Les mots de passe ne correspondent pas.");
            request.getRequestDispatcher("/changer-mot-de-passe.jsp").forward(request, response);
            return;
        }

        String hash = PasswordUtil.hashPassword(nouveau);
        utilisateurDao.updateMotDePasse(user.getId(), hash);
        utilisateurDao.updateDoitChangerMotDePasse(user.getId(), false); // nouvelle méthode dans ton DAO

        user.setMotDePasse(hash);
        user.setDoitChangerMotDePasse(false);
        session.setAttribute("user", user);

        // Redirection vers le dashboard
        switch (user.getRole()) {
            case ADMIN -> response.sendRedirect(request.getContextPath() + "/admin/utilisateurs");
            case GESTIONNAIRE -> response.sendRedirect(request.getContextPath() + "/gestionnaire/dashboard");
            case UTILISATEUR -> response.sendRedirect(request.getContextPath() + "/utilisateur/dashboard");
        }
    }
}
