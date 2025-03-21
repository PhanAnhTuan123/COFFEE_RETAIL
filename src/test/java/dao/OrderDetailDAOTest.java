package dao;

import com.example.cafenetworksolution.dao.OrderDAO;
import com.example.cafenetworksolution.dao.OrderDetailDAO;
import com.example.cafenetworksolution.entity.Order;
import com.example.cafenetworksolution.entity.OrderDetail;
import com.example.cafenetworksolution.entity.Product;
import com.example.cafenetworksolution.entity.User;
import com.example.cafenetworksolution.entity.enumeration.OrderStatus;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderDetailDAOTest {
    private static OrderDAO orderDAO;
    private static OrderDetailDAO detailDAO;
    private static Integer orderId, detailId;

    @BeforeAll
    static void setup() {
        orderDAO = new OrderDAO();
        detailDAO = new OrderDetailDAO();
        Order o = new Order();
        User u = new User(); u.setId(1);
        o.setUser(u);
        o.setTotalPrice(0.0);
        o.setStatus(OrderStatus.Pending);
        orderId = orderDAO.add(o);
        assertNotNull(orderId);
    }

    @AfterAll
    static void cleanup() {
        orderDAO.delete(orderId);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void testAddAndGetById() {
        OrderDetail d = new OrderDetail();
        Order o = new Order(); o.setId(orderId);
        Product p = new Product(); p.setId(1);
        d.setOrder(o);
        d.setProduct(p);
        d.setQuantity(4);
        d.setSubtotal(100.0);

        detailId = detailDAO.add(d);
        assertNotNull(detailId);
        assertTrue(detailId > 0);

        OrderDetail f = detailDAO.getById(detailId);
        assertNotNull(f);
        assertEquals(orderId, f.getOrder().getId());
        assertEquals(1, f.getProduct().getId());
        assertEquals(4, f.getQuantity());
        assertEquals(100.0, f.getSubtotal());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void testUpdate() {
        OrderDetail d = detailDAO.getById(detailId);
        d.setQuantity(10);
        d.setSubtotal(250.0);
        assertTrue(detailDAO.update(d));

        OrderDetail u = detailDAO.getById(detailId);
        assertEquals(10, u.getQuantity());
        assertEquals(250.0, u.getSubtotal());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testGetAll() {
        List<OrderDetail> all = detailDAO.getAll();
        assertFalse(all.isEmpty());
        assertTrue(all.stream().anyMatch(x -> x.getId().equals(detailId)));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void testDelete() {
        assertTrue(detailDAO.delete(detailId));
        assertNull(detailDAO.getById(detailId));
    }
}
