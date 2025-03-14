package com.example.coffeeretail.dao;

import com.example.coffeeretail.entity.Category;
import com.example.coffeeretail.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CategoryDAO {
    public Category findById(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    public List<Category> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c", Category.class)
                    .getResultList();
        } finally { em.close(); }
    }

    public void save(Category c) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();
    }

    public Category update(Category c) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Category merged = em.merge(c);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Category c = em.find(Category.class, id);
        if (c != null) em.remove(c);
        em.getTransaction().commit();
        em.close();
    }
}
