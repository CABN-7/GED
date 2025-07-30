package com.groupeisi.ged.web;

import com.groupeisi.ged.dao.UtilisateurDao;
import com.groupeisi.ged.model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test-utilisateur")
public class TestUtilisateurServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UtilisateurDao dao = new UtilisateurDao();
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("SOW");
        utilisateur.setPrenom("Moussa");
        utilisateur.setEmail("moussa@example.com");
        utilisateur.setMotDePasse("123456");

        dao.create(utilisateur);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Utilisateur ajouté avec succès !</h1>");
    }
}
