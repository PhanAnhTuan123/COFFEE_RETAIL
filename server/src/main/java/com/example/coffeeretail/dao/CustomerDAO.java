package com.example.coffeeretail.dao;

import com.example.coffeeretail.entity.Customer;
import com.example.coffeeretail.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CustomerDAO {
    public Customer findById(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public List<Customer> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Customer c", Customer.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Customer c) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();
    }

    public Customer update(Customer c) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Customer merged = em.merge(c);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Customer c = em.find(Customer.class, id);
        if (c != null) em.remove(c);
        em.getTransaction().commit();
        em.close();
    }
}
