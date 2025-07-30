package com.groupeisi.ged.dao;

import com.groupeisi.ged.model.Utilisateur;
import com.groupeisi.ged.model.Utilisateur.Role;
import com.groupeisi.ged.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UtilisateurDao {

    // Récupérer tous les utilisateurs (sans pagination)
    public List<Utilisateur> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        List<Utilisateur> utilisateurs = null;
        try {
            TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class);
            utilisateurs = query.getResultList();
        } finally {
            em.close();
        }
        return utilisateurs;
    }

    // Récupérer une page d’utilisateurs (pagination)
    public List<Utilisateur> findAllPaged(int page, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Utilisateur> utilisateurs = null;
        try {
            TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u ORDER BY u.nom", Utilisateur.class);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            utilisateurs = query.getResultList();
        } finally {
            em.close();
        }
        return utilisateurs;
    }

    // Compter tous les utilisateurs
    public long countAll() {
        EntityManager em = JpaUtil.getEntityManager();
        long count = 0;
        try {
            count = em.createQuery("SELECT COUNT(u) FROM Utilisateur u", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
        return count;
    }

    // Compter les utilisateurs par rôle
    public long countUsersByRole(Role role) {
        EntityManager em = JpaUtil.getEntityManager();
        long count = 0;
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(u) FROM Utilisateur u WHERE u.role = :role", Long.class);
            query.setParameter("role", role);
            count = query.getSingleResult();
        } finally {
            em.close();
        }
        return count;
    }

    // Trouver utilisateur par ID
    public Utilisateur findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        Utilisateur utilisateur = null;
        try {
            utilisateur = em.find(Utilisateur.class, id);
        } finally {
            em.close();
        }
        return utilisateur;
    }

    // Créer un nouvel utilisateur
    public void create(Utilisateur utilisateur) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(utilisateur);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    // Mettre à jour un utilisateur existant
    public void update(Utilisateur utilisateur) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(utilisateur);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    // Activer ou désactiver un utilisateur par ID
    public void updateActif(Long id, boolean actif) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Utilisateur user = em.find(Utilisateur.class, id);
            if (user != null) {
                user.setActif(actif);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Supprimer un utilisateur par ID
    public void delete(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Utilisateur utilisateur = em.find(Utilisateur.class, id);
            if (utilisateur != null) {
                em.remove(utilisateur);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    // Trouver utilisateur par email (utile pour login)
    public Utilisateur findByEmail(String email) {
        EntityManager em = JpaUtil.getEntityManager();
        Utilisateur utilisateur = null;
        try {
            TypedQuery<Utilisateur> query = em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class);
            query.setParameter("email", email);
            utilisateur = query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
        return utilisateur;
    }

    public void updateMotDePasse(Long userId, String nouveauHash) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        Utilisateur u = em.find(Utilisateur.class, userId);
        u.setMotDePasse(nouveauHash);
        em.getTransaction().commit();
        em.close();
    }

    public void updateDoitChangerMotDePasse(Long userId, boolean value) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        Utilisateur u = em.find(Utilisateur.class, userId);
        u.setDoitChangerMotDePasse(value);
        em.getTransaction().commit();
        em.close();
    }

    public List<Utilisateur> findAllByRole(Role role) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Utilisateur> query = em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.role = :role", Utilisateur.class);
            query.setParameter("role", role);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


}
