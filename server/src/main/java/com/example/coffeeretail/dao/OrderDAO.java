package com.example.coffeeretail.dao;

import com.example.coffeeretail.entity.Order;
import com.example.coffeeretail.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class OrderDAO {
    public Order findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Order.class, id);
        } finally {
            em.close();
        }
    }

    public List<Order> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT o FROM Order o", Order.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Order o) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
    }

    public Order update(Order o) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Order merged = em.merge(o);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Order o = em.find(Order.class, id);
        if (o != null) em.remove(o);
        em.getTransaction().commit();
        em.close();
    }
}

