package com.groupeisi.ged.listener;

import com.groupeisi.ged.model.Utilisateur;
import com.groupeisi.ged.utils.PasswordUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class InitAppListener implements ServletContextListener {

    private EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        emf = Persistence.createEntityManagerFactory("gedPU");
        EntityManager em = emf.createEntityManager();

        try {
            long adminCount = (long) em.createQuery(
                            "SELECT COUNT(u) FROM Utilisateur u WHERE u.role = :role"
                    ).setParameter("role", Utilisateur.Role.ADMIN) // ✅ Utilisation correcte de l'enum
                    .getSingleResult();

            if (adminCount == 0) {
                em.getTransaction().begin();

                Utilisateur admin = new Utilisateur();
                admin.setNom("Admin");
                admin.setPrenom("Principal");
                admin.setEmail("admin@yopmail.com");
                admin.setMotDePasse(PasswordUtil.hashPassword("admin123"));
                admin.setRole(Utilisateur.Role.ADMIN);

                em.persist(admin);
                em.getTransaction().commit();

                System.out.println("✅ Admin par défaut créé avec succès.");
            } else {
                System.out.println("ℹ️ Admin déjà présent en base.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
