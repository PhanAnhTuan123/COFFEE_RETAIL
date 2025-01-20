package com.example.cafenetworksolution;

import com.example.cafenetworksolution.entity.enumeration.Role;
import com.example.cafenetworksolution.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDateTime;

public class Main {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CafeNetworkSolutionPU");
    static EntityManager em = emf.createEntityManager();
    static Faker faker = new Faker();

    public static void main(String[] args) {

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

    public static void demoFakeDataWithDatafaker() {
        try {
        em.getTransaction().begin();

        User user = new User();
        user.setRole(Role.Manager);
        user.setPhone(faker.phoneNumber().phoneNumber());
        user.setEmail(faker.internet().emailAddress());
        user.setUsername(faker.name().username());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

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
