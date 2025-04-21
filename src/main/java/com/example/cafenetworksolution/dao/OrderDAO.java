// src/main/java/com/example/cafenetworksolution/dao/OrderDAO.java
package com.example.cafenetworksolution.dao;

import com.example.cafenetworksolution.config.DatabaseConnection;
import com.example.cafenetworksolution.entity.Order;
import com.example.cafenetworksolution.entity.User;
import com.example.cafenetworksolution.entity.enumeration.OrderStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    /** Thêm mới Order, trả về ID sinh ra */
    public Integer add(Order order) {
        // bảng Orders, cột order_date có DEFAULT nên ta chỉ set userID, total_price, status
        String sql = "INSERT INTO [Orders] (userID, total_price, status) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getUser().getId());
            stmt.setDouble(2, order.getTotalPrice());
            stmt.setString(3, order.getStatus().name());

            int affected = stmt.executeUpdate();
            if (affected == 0) throw new SQLException("Tạo Order thất bại, không có hàng nào bị ảnh hưởng.");

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else throw new SQLException("Tạo Order thất bại, không lấy được ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Lấy Order theo ID */
    public Order getById(Integer id) {
        String sql = "SELECT id, userID, order_date, total_price, status FROM [Orders] WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;

                Order o = new Order();
                o.setId(rs.getInt("id"));
                User u = new User(); u.setId(rs.getInt("userID"));
                o.setUser(u);
                o.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                o.setTotalPrice(rs.getDouble("total_price"));
                o.setStatus(OrderStatus.valueOf(rs.getString("status")));
                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Cập nhật total_price và status */
    public boolean update(Order order) {
        String sql = "UPDATE [Orders] SET total_price = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, order.getTotalPrice());
            stmt.setString(2, order.getStatus().name());
            stmt.setInt(3, order.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Xóa Order theo ID */
    public boolean delete(Integer id) {
        String sql = "DELETE FROM [Orders] WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Lấy tất cả Order */
    public List<Order> getAll() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT id, userID, order_date, total_price, status FROM [Orders]";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                User u = new User(); u.setId(rs.getInt("userID"));
                o.setUser(u);
                o.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                o.setTotalPrice(rs.getDouble("total_price"));
                o.setStatus(OrderStatus.valueOf(rs.getString("status")));
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
