package com.groupeisi.ged.dao;

import com.groupeisi.ged.model.Document;
import jakarta.persistence.*;

import java.util.List;

public class DocumentDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gedPU");

    public List<Document> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Document d", Document.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Document findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Document.class, id);
        } finally {
            em.close();
        }
    }

    public long countAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(d) FROM Document d", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public long countByStatus(String statut) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(d) FROM Document d WHERE d.statut = :statut", Long.class)
                    .setParameter("statut", statut)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Document> findByStatut(String statut) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Document d WHERE d.statut = :statut", Document.class)
                    .setParameter("statut", statut)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void updateStatut(Long documentId, String nouveauStatut) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Document doc = em.find(Document.class, documentId);
            if (doc != null) {
                doc.setStatut(nouveauStatut);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void create(Document document) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(document);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Document doc = em.find(Document.class, id);
            if (doc != null) {
                em.remove(doc);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Document> findWithFilters(String statut, String titre, String proprietaireNom) {
        EntityManager em = emf.createEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT d FROM Document d WHERE 1=1");

            if (statut != null && !statut.isEmpty()) {
                jpql.append(" AND d.statut = :statut");
            }
            if (titre != null && !titre.isEmpty()) {
                jpql.append(" AND LOWER(d.titre) LIKE :titre");
            }
            if (proprietaireNom != null && !proprietaireNom.isEmpty()) {
                jpql.append(" AND LOWER(d.proprietaire.nom) LIKE :nom");
            }

            TypedQuery<Document> query = em.createQuery(jpql.toString(), Document.class);

            if (statut != null && !statut.isEmpty()) {
                query.setParameter("statut", statut);
            }
            if (titre != null && !titre.isEmpty()) {
                query.setParameter("titre", "%" + titre.toLowerCase() + "%");
            }
            if (proprietaireNom != null && !proprietaireNom.isEmpty()) {
                query.setParameter("nom", "%" + proprietaireNom.toLowerCase() + "%");
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Document> findByProprietaireId(Long idProprietaire) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Document d WHERE d.proprietaire.id = :id", Document.class)
                    .setParameter("id", idProprietaire)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Document> findByUtilisateurWithFilters(Long utilisateurId, String statut, String titre) {
        EntityManager em = emf.createEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT d FROM Document d WHERE d.proprietaire.id = :uid");

            if (statut != null && !statut.isEmpty()) {
                jpql.append(" AND d.statut = :statut");
            }
            if (titre != null && !titre.isEmpty()) {
                jpql.append(" AND LOWER(d.titre) LIKE :titre");
            }

            TypedQuery<Document> query = em.createQuery(jpql.toString(), Document.class);
            query.setParameter("uid", utilisateurId);

            if (statut != null && !statut.isEmpty()) {
                query.setParameter("statut", statut);
            }
            if (titre != null && !titre.isEmpty()) {
                query.setParameter("titre", "%" + titre.toLowerCase() + "%");
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void update(Document document) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(document);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }


}
