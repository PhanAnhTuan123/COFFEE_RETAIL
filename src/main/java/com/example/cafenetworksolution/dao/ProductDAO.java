package com.coffeeshop.dao;

import com.coffeeshop.config.DatabaseConnection;
import com.coffeeshop.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAO {
    private static final Logger logger = LoggerFactory.getLogger(com.example.cafenetworksolution.dao.CategoryDAO.CategoryDAO.class);

    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name, description, created_at, updated_at FROM categories";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                category.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                categories.add(category);
            }
        } catch (SQLException e) {
            logger.error("Lỗi khi lấy danh sách danh mục", e);
        }

        return categories;
    }

    public Optional<Category> findById(Long id) {
        String sql = "SELECT id, name, description, created_at, updated_at FROM categories WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getLong("id"));
                    category.setName(rs.getString("name"));
                    category.setDescription(rs.getString("description"));
                    category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    category.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    return Optional.of(category);
                }
            }
        } catch (SQLException e) {
            logger.error("Lỗi khi tìm danh mục theo ID: " + id, e);
        }

        return Optional.empty();
    }

    public Category save(Category category) {
        if (category.getId() == null) {
            return insert(category);
        } else {
            return update(category);
        }
    }

    private Category insert(Category category) {
        String sql = "INSERT INTO categories (name, description, created_at, updated_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            LocalDateTime now = LocalDateTime.now();
            category.setCreatedAt(now);
            category.setUpdatedAt(now);

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(category.getCreatedAt()));
            stmt.setTimestamp(4, Timestamp.valueOf(category.getUpdatedAt()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Tạo danh mục thất bại, không có dòng nào được thêm vào");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Tạo danh mục thất bại, không lấy được ID");
                }
            }
        } catch (SQLException e) {
            logger.error("Lỗi khi thêm danh mục mới", e);
        }

        return category;
    }

    private Category update(Category category) {
        String sql = "UPDATE categories SET name = ?, description = ?, updated_at = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            LocalDateTime now = LocalDateTime.now();
            category.setUpdatedAt(now);

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(category.getUpdatedAt()));
            stmt.setLong(4, category.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Lỗi khi cập nhật danh mục với ID: " + category.getId(), e);
        }

        return category;
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM categories WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Lỗi khi xóa danh mục với ID: " + id, e);
            return false;
        }
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM categories WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Lỗi khi kiểm tra tồn tại danh mục với ID: " + id, e);
        }

        return false;
    }
}