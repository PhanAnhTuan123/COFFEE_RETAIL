package com.example.cafenetworksolution;

import com.example.cafenetworksolution.entity.OrderDetail;
import com.example.cafenetworksolution.entity.Product;
import com.example.cafenetworksolution.entity.enumeration.Role;
import com.example.cafenetworksolution.entity.User;
import com.example.cafenetworksolution.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CafeNetworkSolutionPU");
        EntityManager em = emf.createEntityManager();

        try {
            // Giả sử bạn đã có User và Product trong DB
            User user    = em.find(User.class, 1);       // user id=1
            Product p1   = em.find(Product.class, 1);    // product id=1
            Product p2   = em.find(Product.class, 2);    // product id=2

            // Tạo danh sách chi tiết
            List<OrderDetail> details = new ArrayList<>();

            OrderDetail d1 = new OrderDetail();
            d1.setProduct(p1);
            d1.setQuantity(2);
            d1.setSubtotal(2 * p1.getPrice());     // giả sử getter unitPrice
            details.add(d1);

            OrderDetail d2 = new OrderDetail();
            d2.setProduct(p2);
            d2.setQuantity(1);
            d2.setSubtotal(1 * p2.getPrice());
            details.add(d2);

            // Gọi service
            OrderService svc = new OrderService(em);
            var order = svc.createOrderWithDetails(user, details);

            System.out.println("Order vừa tạo có ID = " + order.getId());
            System.out.println("Tổng tiền = " + order.getTotalPrice());

        } finally {
            if (em.isOpen()) em.close();
            if (emf.isOpen()) emf.close();
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
