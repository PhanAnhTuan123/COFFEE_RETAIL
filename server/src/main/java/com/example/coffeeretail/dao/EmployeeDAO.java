package com.example.coffeeretail.dao;

import com.example.coffeeretail.entity.Employee;
import com.example.coffeeretail.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EmployeeDAO {
    public Employee findById(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Employee e) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
        em.close();
    }

    public Employee update(Employee e) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Employee merged = em.merge(e);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Employee e = em.find(Employee.class, id);
        if (e != null) em.remove(e);
        em.getTransaction().commit();
        em.close();
    }
}
