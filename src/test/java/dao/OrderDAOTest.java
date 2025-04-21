package dao;

import com.example.cafenetworksolution.dao.OrderDAO;
import com.example.cafenetworksolution.entity.Order;
import com.example.cafenetworksolution.entity.User;
import com.example.cafenetworksolution.entity.enumeration.OrderStatus;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderDAOTest {
    private static OrderDAO dao;
    private static Integer orderId;

    @BeforeAll
    static void init() {
        dao = new OrderDAO();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void testAddAndGetById() {
        Order o = new Order();
        User u = new User(); u.setId(1);
        o.setUser(u);
        o.setTotalPrice(123.45);
        o.setStatus(OrderStatus.Pending);

        orderId = dao.add(o);
        assertNotNull(orderId);
        assertTrue(orderId > 0);

        Order f = dao.getById(orderId);
        assertNotNull(f);
        assertEquals(orderId, f.getId());
        assertEquals(1, f.getUser().getId());
        assertEquals(123.45, f.getTotalPrice());
        assertEquals(OrderStatus.Pending, f.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void testUpdate() {
        Order o = dao.getById(orderId);
        o.setTotalPrice(999.0);
        o.setStatus(OrderStatus.Completed);
        assertTrue(dao.update(o));

        Order u = dao.getById(orderId);
        assertEquals(999.0, u.getTotalPrice());
        assertEquals(OrderStatus.Completed, u.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testGetAll() {
        List<Order> all = dao.getAll();
        assertFalse(all.isEmpty());
        assertTrue(all.stream().anyMatch(x -> x.getId().equals(orderId)));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void testDelete() {
        assertTrue(dao.delete(orderId));
        assertNull(dao.getById(orderId));
    }
}