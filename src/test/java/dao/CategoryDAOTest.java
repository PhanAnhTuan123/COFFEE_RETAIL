package dao;

import com.example.cafenetworksolution.dao.CategoryDAO;
import com.example.cafenetworksolution.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

public class CategoryDAOTest {

    private CategoryDAO categoryDAO;

    @BeforeEach
    public void setUp() {
        categoryDAO = new CategoryDAO();
    }

    @AfterEach
    public void tearDown() {
        // Cleanup logic if needed
    }

    @Test
    public void testAddCategory() {
        Category category = new Category();
        category.setName("Test Category");

        Integer addedId = categoryDAO.add(category);

        System.out.println("===> ID vừa thêm: " + addedId);
        System.out.println("===> Danh sách sau khi thêm:");
        for (Category c : categoryDAO.getAll()) {
            System.out.println("    - " + c.getId() + " | " + c.getName());
        }

        assertNotNull(addedId, "ID không được null sau khi thêm");
        assertTrue(addedId > 0, "ID phải lớn hơn 0");

        Category savedCategory = categoryDAO.getById(addedId);
        assertNotNull(savedCategory, "Không tìm thấy category sau khi thêm");
        assertEquals("Test Category", savedCategory.getName(), "Tên category không khớp");
        assertNotNull(savedCategory.getCreatedAt(), "createdAt không được thiết lập");
        assertNotNull(savedCategory.getUpdatedAt(), "updatedAt không được thiết lập");
    }


    @Test
    public void testUpdateCategory() {
        Category category = new Category();
        category.setName("Category To Update");
        Integer categoryId = categoryDAO.add(category);

        Category beforeUpdate = categoryDAO.getById(categoryId);
        LocalDateTime oldUpdatedAt = beforeUpdate.getUpdatedAt();

        try {
            Thread.sleep(100); // để đảm bảo updatedAt sẽ thay đổi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        beforeUpdate.setName("Updated Category Name");
        boolean updateResult = categoryDAO.update(beforeUpdate);

        assertTrue(updateResult, "Cập nhật thất bại");

        Category afterUpdate = categoryDAO.getById(categoryId);
        assertEquals("Updated Category Name", afterUpdate.getName(), "Tên không được cập nhật");
        assertTrue(afterUpdate.getUpdatedAt().isAfter(oldUpdatedAt), "updatedAt không được cập nhật");
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category();
        category.setName("Category To Delete");
        Integer categoryId = categoryDAO.add(category);

        boolean deleteResult = categoryDAO.delete(categoryId);
        assertTrue(deleteResult, "Xóa thất bại");

        Category deletedCategory = categoryDAO.getById(categoryId);
        assertNull(deletedCategory, "Category vẫn tồn tại sau khi xóa");
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = categoryDAO.getAll();
        assertNotNull(categories, "Danh sách categories không được null");
        assertTrue(categories.size() >= 0, "Danh sách categories phải có ít nhất 0 phần tử");
    }
    @Test
    public void testCreate() {
        // test logic
    }
}
