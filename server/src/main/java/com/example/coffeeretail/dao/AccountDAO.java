package com.example.coffeeretail.dao;

import com.example.coffeeretail.entity.Account;
import com.example.coffeeretail.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AccountDAO {
    public Account findByUsername(String username) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Account.class, username);
        } finally {
            em.close();
        }
    }

    public List<Account> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Account a", Account.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Account a) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.close();
    }

    public Account update(Account a) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Account merged = em.merge(a);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(String username) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Account a = em.find(Account.class, username);
        if (a != null) em.remove(a);
        em.getTransaction().commit();
        em.close();
    }
}
