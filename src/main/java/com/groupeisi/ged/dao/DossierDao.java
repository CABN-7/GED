package com.groupeisi.ged.dao;

import com.groupeisi.ged.model.Dossier;
import com.groupeisi.ged.model.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class DossierDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gedPU");

    /**
     * Trouve ou crée le dossier racine pour un utilisateur
     */
    public Dossier findOrCreateRacineForUser(Utilisateur utilisateur) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Dossier> racines = em.createQuery(
                            "SELECT d FROM Dossier d WHERE d.proprietaire.id = :userId AND d.isRacine = true",
                            Dossier.class)
                    .setParameter("userId", utilisateur.getId())
                    .getResultList();

            if (!racines.isEmpty()) {
                return racines.get(0);
            }

            // Créer le dossier racine s'il n'existe pas
            Dossier racine = new Dossier("Racine", utilisateur);
            racine.setRacine(true);

            em.getTransaction().begin();
            em.persist(racine);
            em.getTransaction().commit();

            return racine;
        } finally {
            em.close();
        }
    }

    /**
     * Récupère le dossier racine avec tous ses sous-dossiers et documents pour un utilisateur
     */
    public Dossier getRootFolderWithHierarchy(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            // Récupérer le dossier racine
            List<Dossier> racines = em.createQuery(
                            "SELECT d FROM Dossier d WHERE d.proprietaire.id = :userId AND d.isRacine = true",
                            Dossier.class)
                    .setParameter("userId", userId)
                    .getResultList();

            if (racines.isEmpty()) {
                return null; // Pas de dossier racine trouvé
            }

            Dossier racine = racines.get(0);

            // Charger tous les sous-dossiers de l'utilisateur
            List<Dossier> tousSousDossiers = em.createQuery(
                            "SELECT d FROM Dossier d WHERE d.proprietaire.id = :userId AND d.isRacine = false",
                            Dossier.class)
                    .setParameter("userId", userId)
                    .getResultList();

            // Force le chargement des documents de la racine
            if (racine.getDocuments() != null) {
                racine.getDocuments().size();
            }

            // Charger récursivement tous les sous-dossiers
            loadDossierHierarchy(racine, em);

            return racine;
        } finally {
            em.close();
        }
    }

    /**
     * Charge récursivement la hiérarchie d'un dossier
     */
    private void loadDossierHierarchy(Dossier dossier, EntityManager em) {
        // Force le chargement des documents
        if (dossier.getDocuments() != null) {
            dossier.getDocuments().size();
        }

        // Force le chargement des sous-dossiers
        if (dossier.getSousDossiers() != null) {
            dossier.getSousDossiers().size();
            for (Dossier sousDossier : dossier.getSousDossiers()) {
                loadDossierHierarchy(sousDossier, em);
            }
        }
    }

    /**
     * Trouve tous les dossiers d'un utilisateur (racine + sous-dossiers)
     */
    public List<Dossier> findAllByUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT d FROM Dossier d WHERE d.proprietaire.id = :userId ORDER BY d.isRacine DESC, d.nom",
                            Dossier.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Crée un nouveau dossier sous la racine d'un utilisateur
     */
//    public void createSousDossier(String nom, Long userId) {
//        EntityManager em = emf.createEntityManager();
//        try {
//            // Récupérer l'utilisateur et son dossier racine
//            Utilisateur utilisateur = em.find(Utilisateur.class, userId);
//
//            List<Dossier> racines = em.createQuery(
//                            "SELECT d FROM Dossier d WHERE d.proprietaire.id = :userId AND d.isRacine = true",
//                            Dossier.class)
//                    .setParameter("userId", userId)
//                    .getResultList();
//
//            if (racines.isEmpty()) {
//                throw new RuntimeException("Dossier racine non trouvé pour l'utilisateur");
//            }
//
//            Dossier racine = racines.get(0);
//
//            // Créer le nouveau sous-dossier
//            Dossier nouveauDossier = new Dossier(nom, utilisateur);
//            nouveauDossier.setParent(racine);
//            nouveauDossier.setRacine(false);
//
//            em.getTransaction().begin();
//            em.persist(nouveauDossier);
//            em.getTransaction().commit();
//
//        } finally {
//            em.close();
//        }
//    }
    public void createSousDossier(String nom, Long userId, Long parentId) {
        EntityManager em = emf.createEntityManager();
        try {
            Utilisateur utilisateur = em.find(Utilisateur.class, userId);
            Dossier parent = em.find(Dossier.class, parentId);

            if (parent == null) {
                throw new IllegalArgumentException("Dossier parent introuvable");
            }

            Dossier nouveau = new Dossier(nom, utilisateur);
            nouveau.setParent(parent);
            nouveau.setRacine(false);

            em.getTransaction().begin();
            em.persist(nouveau);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }


    // Méthodes existantes conservées
    public List<Dossier> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Dossier d", Dossier.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Dossier findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Dossier.class, id);
        } finally {
            em.close();
        }
    }

    public void create(Dossier dossier) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(dossier);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Dossier dossier = em.find(Dossier.class, id);
            if (dossier != null && !dossier.isRacine()) { // Ne pas supprimer la racine
                em.getTransaction().begin();
                em.remove(dossier);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    public void update(Dossier dossier) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(dossier);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}