package com.example.coffeeretail.dao;

import com.example.coffeeretail.entity.Tables;
import com.example.coffeeretail.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class TableDAO {
    public Tables findById(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Tables.class, id);
        } finally {
            em.close();
        }
    }

    public List<Tables> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT t FROM Tables t", Tables.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Tables t) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        em.close();
    }

    public Tables update(Tables t) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Tables merged = em.merge(t);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Tables t = em.find(Tables.class, id);
        if (t != null) em.remove(t);
        em.getTransaction().commit();
        em.close();
    }
}
