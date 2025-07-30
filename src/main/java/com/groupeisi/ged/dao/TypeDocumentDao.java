package com.groupeisi.ged.dao;

import com.groupeisi.ged.model.TypeDocument;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class TypeDocumentDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gedPU");

    public List<TypeDocument> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM TypeDocument t", TypeDocument.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public TypeDocument findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(TypeDocument.class, id);
        } finally {
            em.close();
        }
    }

    public void create(TypeDocument typeDocument) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(typeDocument);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypeDocument type = em.find(TypeDocument.class, id);
            if (type != null) {
                em.getTransaction().begin();
                em.remove(type);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}
