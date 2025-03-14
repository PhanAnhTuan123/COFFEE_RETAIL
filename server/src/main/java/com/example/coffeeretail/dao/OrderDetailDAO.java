package com.example.coffeeretail.dao;

import com.example.coffeeretail.entity.OrderDetail;
import com.example.coffeeretail.entity.OrderDetailId;
import com.example.coffeeretail.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class OrderDetailDAO {
    public OrderDetail findById(OrderDetailId id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(OrderDetail.class, id);
        } finally { em.close(); }
    }

    public List<OrderDetail> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT d FROM OrderDetail d", OrderDetail.class)
                    .getResultList();
        } finally { em.close(); }
    }

    public void save(OrderDetail d) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(d);
        em.getTransaction().commit();
        em.close();
    }

    public OrderDetail update(OrderDetail d) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        OrderDetail merged = em.merge(d);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(OrderDetailId id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        OrderDetail d = em.find(OrderDetail.class, id);
        if (d != null) em.remove(d);
        em.getTransaction().commit();
        em.close();
    }
}
