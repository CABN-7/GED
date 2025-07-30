package com.groupeisi.ged.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static final String PERSISTENCE_UNIT_NAME = "gedPU";
    public static EntityManagerFactory emf;

    // Initialisation statique une seule fois
    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Erreur lors de la création de EntityManagerFactory");
        }
    }

    // Récupérer un EntityManager
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Fermer l'EntityManagerFactory à la fin (facultatif mais conseillé dans les applis standalone)
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
