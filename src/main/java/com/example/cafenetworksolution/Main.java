package com.example.cafenetworksolution;

import com.example.cafenetworksolution.entities.Cafe;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CafeNetworkSolutionPU");
        EntityManager em = emf.createEntityManager();

        try {
            // Bắt đầu giao dịch
            em.getTransaction().begin();

            // Tạo một đối tượng mới (ví dụ: Cafe)
            Cafe cafe = new Cafe();
            cafe.setName("Sunset Cafe");
            cafe.setAddress("123 Main Street");
            cafe.setOwner("John Doe");

            // Lưu đối tượng vào database
            em.persist(cafe);

            // Kết thúc giao dịch
            em.getTransaction().commit();

            System.out.println("Cafe saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
        }
    }
}
