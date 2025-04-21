// src/main/java/com/example/cafenetworksolution/dao/OrderDetailDAO.java
package com.example.cafenetworksolution.dao;

import com.example.cafenetworksolution.config.DatabaseConnection;
import com.example.cafenetworksolution.entity.Order;
import com.example.cafenetworksolution.entity.OrderDetail;
import com.example.cafenetworksolution.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {

    /** Thêm mới OrderDetail, trả về ID sinh ra */
    public Integer add(OrderDetail detail) {
        String sql = "INSERT INTO [OrderDetail] (order_id, product_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, detail.getOrder().getId());
            stmt.setInt(2, detail.getProduct().getId());
            stmt.setInt(3, detail.getQuantity());
            stmt.setDouble(4, detail.getSubtotal());

            int affected = stmt.executeUpdate();
            if (affected == 0) throw new SQLException("Tạo OrderDetail thất bại, không có hàng nào bị ảnh hưởng.");

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else throw new SQLException("Tạo OrderDetail thất bại, không lấy được ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Lấy OrderDetail theo ID */
    public OrderDetail getById(Integer id) {
        String sql = "SELECT id, order_id, product_id, quantity, subtotal FROM [OrderDetail] WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;

                OrderDetail d = new OrderDetail();
                d.setId(rs.getInt("id"));

                Order o = new Order(); o.setId(rs.getInt("order_id"));
                d.setOrder(o);

                Product p = new Product(); p.setId(rs.getInt("product_id"));
                d.setProduct(p);

                d.setQuantity(rs.getInt("quantity"));
                d.setSubtotal(rs.getDouble("subtotal"));
                return d;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Cập nhật OrderDetail */
    public boolean update(OrderDetail detail) {
        String sql = "UPDATE [OrderDetail] SET order_id = ?, product_id = ?, quantity = ?, subtotal = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, detail.getOrder().getId());
            stmt.setInt(2, detail.getProduct().getId());
            stmt.setInt(3, detail.getQuantity());
            stmt.setDouble(4, detail.getSubtotal());
            stmt.setInt(5, detail.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Xóa OrderDetail theo ID */
    public boolean delete(Integer id) {
        String sql = "DELETE FROM [OrderDetail] WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Lấy tất cả OrderDetail */
    public List<OrderDetail> getAll() {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT id, order_id, product_id, quantity, subtotal FROM [OrderDetail]";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                OrderDetail d = new OrderDetail();
                d.setId(rs.getInt("id"));

                Order o = new Order(); o.setId(rs.getInt("order_id"));
                d.setOrder(o);

                Product p = new Product(); p.setId(rs.getInt("product_id"));
                d.setProduct(p);

                d.setQuantity(rs.getInt("quantity"));
                d.setSubtotal(rs.getDouble("subtotal"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
