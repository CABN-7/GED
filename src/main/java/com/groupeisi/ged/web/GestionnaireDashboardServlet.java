package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.DocumentDao;
import com.groupeisi.ged.model.Document;
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

@WebServlet("/gestionnaire/dashboard")
public class GestionnaireDashboardServlet extends HttpServlet {

    private UtilisateurDao utilisateurDao;
    private DocumentDao documentDao;

    @Override
    public void init() {
        utilisateurDao = new UtilisateurDao();
        documentDao = new DocumentDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null || user.getRole() != Role.GESTIONNAIRE || !user.isActif()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit");
            return;
        }

        String statutFiltre = request.getParameter("statut");
        String titreFiltre = request.getParameter("titre");
        String utilisateurFiltre = request.getParameter("proprietaire");

        List<Document> documentsFiltres = documentDao.findWithFilters(
                statutFiltre,
                titreFiltre,
                utilisateurFiltre
        );

        request.setAttribute("documents", documentsFiltres);
        request.setAttribute("statutFiltre", statutFiltre);
        request.setAttribute("titreFiltre", titreFiltre);
        request.setAttribute("proprietaireFiltre", utilisateurFiltre);

        // Statistiques et utilisateurs
        request.setAttribute("totalDocuments", documentDao.countAll());
        request.setAttribute("documentsValidés", documentDao.countByStatus("VALIDÉ"));
        request.setAttribute("documentsRejetés", documentDao.countByStatus("REJETÉ"));
        request.setAttribute("documentsEnAttente", documentDao.countByStatus("EN_ATTENTE"));
        request.setAttribute("utilisateursSimples", utilisateurDao.findAllByRole(Role.UTILISATEUR));

        request.getRequestDispatcher("/WEB-INF/pages/gestionnaire/dashboard.jsp").forward(request, response);
    }

}
