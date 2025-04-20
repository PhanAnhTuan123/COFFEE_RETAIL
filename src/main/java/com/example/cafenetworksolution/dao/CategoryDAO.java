package com.example.cafenetworksolution.dao;

import com.example.cafenetworksolution.config.DatabaseConnection;
import com.example.cafenetworksolution.entity.Category;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

        public Integer add(Category category) {
                String sql = "INSERT INTO categories (name, created_at, updated_at) VALUES (?, ?, ?)";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        LocalDateTime now = LocalDateTime.now();
                        category.setCreatedAt(now);
                        category.setUpdatedAt(now);

                        stmt.setString(1, category.getName());
                        stmt.setTimestamp(2, Timestamp.valueOf(category.getCreatedAt()));
                        stmt.setTimestamp(3, Timestamp.valueOf(category.getUpdatedAt()));

                        int affectedRows = stmt.executeUpdate();
                        if (affectedRows == 0) {
                                throw new SQLException("Creating category failed, no rows affected.");
                        }

                        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                        return Math.toIntExact(generatedKeys.getLong(1));
                                } else {
                                        throw new SQLException("Creating category failed, no ID obtained.");
                                }
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null;
        }

        public Category getById(Integer id) {
                String sql = "SELECT id, name, created_at, updated_at FROM categories WHERE id = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, id);
                        try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next()) {
                                        Category category = new Category();
                                        category.setId(rs.getInt("id"));
                                        category.setName(rs.getString("name"));
                                        category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                                        category.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                                        return category;
                                }
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null;
        }

        public boolean update(Category category) {
                String sql = "UPDATE categories SET name = ?, updated_at = ? WHERE id = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                        LocalDateTime now = LocalDateTime.now();
                        category.setUpdatedAt(now);

                        stmt.setString(1, category.getName());
                        stmt.setTimestamp(2, Timestamp.valueOf(category.getUpdatedAt()));
                        stmt.setInt(3, category.getId());

                        int affectedRows = stmt.executeUpdate();
                        return affectedRows > 0;
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return false;
        }

        public boolean delete(Integer id) {
                String sql = "DELETE FROM categories WHERE id = ?";
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

        public List<Category> getAll() {
                List<Category> categories = new ArrayList<>();
                String sql = "SELECT id, name, created_at, updated_at FROM categories";
                try (Connection conn = DatabaseConnection.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                                Category category = new Category();
                                category.setId(rs.getInt("id"));
                                category.setName(rs.getString("name"));
                                category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                                category.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                                categories.add(category);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return categories;
        }
}