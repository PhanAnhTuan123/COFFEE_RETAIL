package com.example.cafenetworksolution;

import com.example.cafenetworksolution.entity.Role;
import com.example.cafenetworksolution.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CafeNetworkSolutionPU");
        EntityManager em = emf.createEntityManager();

        try {
            // Transaction begin
            em.getTransaction().begin();

            // Create User
            User user = new User();
            user.setRole(Role.Manager);
            // Save
            em.persist(user);


            // Commit transaction
            em.getTransaction().commit();
            System.out.println("Cafe saved successfully!");

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // Đóng EntityManager và EntityManagerFactory
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
