package com.example.cafenetworksolution.service;

import com.example.cafenetworksolution.entity.Order;
import com.example.cafenetworksolution.entity.OrderDetail;
import com.example.cafenetworksolution.entity.User;
import com.example.cafenetworksolution.entity.enumeration.OrderStatus;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Service layer to handle business logic for Orders and their Details.
 * Manages transactions and cascades persistence to OrderDetail entities.
 */
public class OrderService {
    private final EntityManager em;

    public OrderService(EntityManager em) {
        this.em = em;
    }

    /**
     * Create a new Order along with its OrderDetail items in one transaction.
     * Uses cascade from Order to persist OrderDetail objects.
     *
     * @param user    the User placing the order (must be managed or merged)
     * @param details list of OrderDetail (with product and subtotal set)
     * @return the persisted Order with generated ID and totalPrice
     */
    public Order createOrderWithDetails(User user, List<OrderDetail> details) {
        em.getTransaction().begin();
        try {
            // 1. Initialize Order
            Order order = new Order();
            order.setUser(user);
            order.setStatus(OrderStatus.Pending);

            // 2. Attach details and compute total
            double total = 0.0;
            for (OrderDetail d : details) {
                d.setOrder(order);
                order.getDetails().add(d);
                total += d.getSubtotal();
            }
            order.setTotalPrice(total);

            // 3. Persist order (cascade will persist details)
            em.persist(order);

            // 4. Commit
            em.getTransaction().commit();
            return order;
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        }
    }
}
