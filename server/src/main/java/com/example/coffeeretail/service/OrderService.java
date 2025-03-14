package com.example.coffeeretail.service;

import com.example.coffeeretail.dao.OrderDAO;
import com.example.coffeeretail.dao.OrderDetailDAO;
import com.example.coffeeretail.entity.Order;
import com.example.coffeeretail.entity.OrderDetail;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDao   = new OrderDAO();
    private final OrderDetailDAO detailDao = new OrderDetailDAO();

    /** Tạo order đầy đủ với chi tiết */
    public Order create(Order o, List<OrderDetail> details) {
        orderDao.save(o);
        for (OrderDetail d : details) {
            d.setOrder(o);
            detailDao.save(d);
        }
        return o;
    }

    /** Overload: chỉ truyền userId + chi tiết */
    public Order create(String userId, List<OrderDetail> details) {
        // bạn có thể khởi Order từ userId nếu có User entity
        Order o = new Order();
        o.setId(java.util.UUID.randomUUID().toString());
        o.setDate(java.time.LocalDateTime.now());
        // set totalPrice trong service hoặc client
        return create(o, details);
    }

    public Order get(String id) {
        return orderDao.findById(Long.valueOf(id));
    }

    public List<Order> list() {
        return orderDao.findAll();
    }

    public Order update(Order o) {
        return orderDao.update(o);
    }

    public void delete(String id) {
        orderDao.delete(Long.valueOf(id));
    }
}
