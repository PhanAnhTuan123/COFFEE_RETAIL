package com.example.coffeeretail.dao;

import com.example.coffeeretail.entity.EmployeeDetail;
import com.example.coffeeretail.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EmployeeDetailDAO {
    public EmployeeDetail findById(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(EmployeeDetail.class, id);
        } finally {
            em.close();
        }
    }

    public List<EmployeeDetail> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT d FROM EmployeeDetail d", EmployeeDetail.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(EmployeeDetail d) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(d);
        em.getTransaction().commit();
        em.close();
    }

    public EmployeeDetail update(EmployeeDetail d) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        EmployeeDetail merged = em.merge(d);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        EmployeeDetail d = em.find(EmployeeDetail.class, id);
        if (d != null) em.remove(d);
        em.getTransaction().commit();
        em.close();
    }
}
