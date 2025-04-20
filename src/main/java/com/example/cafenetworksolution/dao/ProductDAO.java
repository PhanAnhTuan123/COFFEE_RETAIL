package com.example.cafenetworksolution.dao;

import com.example.cafenetworksolution.config.DatabaseConnection;
import com.example.cafenetworksolution.entity.Product;
import com.example.cafenetworksolution.entity.Category;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public Product getById(Integer id) {
        String sql = "SELECT id, name, price, stock_quantity, category_id, created_at, updated_at FROM products WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getBigDecimal("price").doubleValue());
                    product.setStockQuantity(rs.getInt("stock_quantity"));
                    product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                    Integer categoryId = rs.getInt("category_id");
                    CategoryDAO categoryDAO = new CategoryDAO();
                    product.setCategory(categoryDAO.getById(categoryId));

                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, stock_quantity = ?, category_id = ?, updated_at = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            LocalDateTime now = LocalDateTime.now();
            product.setUpdatedAt(now);

            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, BigDecimal.valueOf(product.getPrice()));
            stmt.setInt(3, product.getStockQuantity());
            stmt.setInt(4, product.getCategory().getId());
            stmt.setTimestamp(5, Timestamp.valueOf(product.getUpdatedAt()));
            stmt.setInt(6, product.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, stock_quantity, category_id, created_at, updated_at FROM products";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price").doubleValue());
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                Integer categoryId = rs.getInt("category_id");
                CategoryDAO categoryDAO = new CategoryDAO();
                product.setCategory(categoryDAO.getById(categoryId));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getByCategory(Integer categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, stock_quantity, category_id, created_at, updated_at FROM products WHERE category_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getBigDecimal("price").doubleValue());
                    product.setStockQuantity(rs.getInt("stock_quantity"));
                    product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                    CategoryDAO categoryDAO = new CategoryDAO();
                    product.setCategory(categoryDAO.getById(categoryId));

                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Integer add(Product product) {
        String sql = "INSERT INTO products (name, price, stock_quantity, category_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            LocalDateTime now = LocalDateTime.now();
            product.setCreatedAt(now);
            product.setUpdatedAt(now);

            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, BigDecimal.valueOf(product.getPrice()));
            stmt.setInt(3, product.getStockQuantity());
            stmt.setInt(4, product.getCategory().getId());
            stmt.setTimestamp(5, Timestamp.valueOf(product.getCreatedAt()));
            stmt.setTimestamp(6, Timestamp.valueOf(product.getUpdatedAt()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return Math.toIntExact(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}