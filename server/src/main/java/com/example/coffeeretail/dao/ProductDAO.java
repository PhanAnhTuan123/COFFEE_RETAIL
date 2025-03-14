package com.example.coffeeretail.dao;

import com.example.coffeeretail.entity.Product;
import com.example.coffeeretail.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProductDAO {
    public Product findById(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally { em.close(); }
    }

    public List<Product> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Product p", Product.class)
                    .getResultList();
        } finally { em.close(); }
    }

    public void save(Product p) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    public Product update(Product p) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Product merged = em.merge(p);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Product p = em.find(Product.class, id);
        if (p != null) em.remove(p);
        em.getTransaction().commit();
        em.close();
    }
}
