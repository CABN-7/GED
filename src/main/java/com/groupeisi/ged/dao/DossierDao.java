package com.groupeisi.ged.dao;

import com.groupeisi.ged.model.Dossier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class DossierDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gedPU");

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
            if (dossier != null) {
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

    public boolean existsByName(String nom) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery("SELECT COUNT(d) FROM Dossier d WHERE d.nom = :nom", Long.class)
                    .setParameter("nom", nom)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    public Dossier findOrCreateByName(String nom) {
        EntityManager em = emf.createEntityManager();
        Dossier dossier = null;

        try {
            List<Dossier> result = em.createQuery("SELECT d FROM Dossier d WHERE d.nom = :nom", Dossier.class)
                    .setParameter("nom", nom)
                    .getResultList();
            if (!result.isEmpty()) {
                dossier = result.get(0);
            } else {
                dossier = new Dossier(nom);
                em.getTransaction().begin();
                em.persist(dossier);
                em.getTransaction().commit();
            }
            return dossier;
        } finally {
            em.close();
        }
    }

    public List<Dossier> findAllWithDocumentsByUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT d FROM Dossier d LEFT JOIN FETCH d.documents doc " +
                                    "WHERE doc.proprietaire.id = :uid OR SIZE(d.documents) = 0", Dossier.class)
                    .setParameter("uid", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

//    public List<Dossier> findAllParentsWithChildrenAndDocuments(Long userId) {
//        EntityManager em = emf.createEntityManager();
//        try {
//            return em.createQuery(
//                            "SELECT DISTINCT d FROM Dossier d " +
//                                    "LEFT JOIN FETCH d.documents docs " +
//                                    "LEFT JOIN FETCH d.sousDossiers " +
//                                    "WHERE d.parent IS NULL AND (docs IS EMPTY OR docs IS NOT EMPTY)", Dossier.class)
//                    .getResultList();
//        } finally {
//            em.close();
//        }
//    }

//    public List<Dossier> findAllParentsWithChildrenAndDocuments(Long userId) {
//        EntityManager em = emf.createEntityManager();
//        try {
//            return em.createQuery(
//                            "SELECT DISTINCT d FROM Dossier d " +
//                                    "LEFT JOIN FETCH d.documents " +
//                                    "LEFT JOIN FETCH d.sousDossiers " +
//                                    "WHERE d.parent IS NULL", Dossier.class)
//                    .getResultList();
//        } finally {
//            em.close();
//        }
//    }

    public List<Dossier> findAllParentsWithChildrenAndDocuments(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Dossier> dossiers = em.createQuery(
                            "SELECT DISTINCT d FROM Dossier d " +
                                    "LEFT JOIN FETCH d.sousDossiers " +
                                    "WHERE d.parent IS NULL", Dossier.class)
                    .getResultList();

            // ⚠️ Force le chargement des documents (évite lazy loading dans la JSP)
            for (Dossier d : dossiers) {
                d.getDocuments().size(); // force Hibernate à charger les documents
            }

            return dossiers;
        } finally {
            em.close();
        }
    }


    public List<Dossier> findAllParentsWithChildren(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT d FROM Dossier d " +
                                    "LEFT JOIN FETCH d.sousDossiers " +
                                    "WHERE d.parent IS NULL", Dossier.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }


}
