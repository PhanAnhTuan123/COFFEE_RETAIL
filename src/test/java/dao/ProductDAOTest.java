package dao;

import com.example.cafenetworksolution.dao.ProductDAO;
import com.example.cafenetworksolution.dao.CategoryDAO;
import com.example.cafenetworksolution.entity.Category;
import com.example.cafenetworksolution.entity.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDAOTest {

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private Category testCategory;

    @BeforeEach
    public void setUp() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();

        testCategory = new Category();
        testCategory.setName("Test Product Category");
        Integer categoryId = categoryDAO.add(testCategory);
        testCategory.setId(categoryId);
    }

    @AfterEach
    public void tearDown() {
        // Cleanup logic if needed
    }

    @Test
    public void testAddProduct() {
        Product product = createTestProduct("Test Product", 100.0, 50);

        assertNotNull(product.getId(), "ID không được tạo sau khi thêm");

        Product savedProduct = productDAO.getById(product.getId());
        assertNotNull(savedProduct, "Không thể lấy product vừa thêm");
        assertEquals("Test Product", savedProduct.getName(), "Tên product không khớp");
        assertEquals(100.0, savedProduct.getPrice(), 0.001, "Giá product không khớp");
        assertEquals(50L, (long) savedProduct.getStockQuantity(), "Số lượng không khớp");
        assertEquals(testCategory.getId(), savedProduct.getCategory().getId(), "Category ID không khớp");
        assertNotNull(savedProduct.getCreatedAt(), "createdAt không được thiết lập");
        assertNotNull(savedProduct.getUpdatedAt(), "updatedAt không được thiết lập");
    }

    @Test
    public void testUpdateProduct() {
        Product product = createTestProduct("Product To Update", 200.0, 30);

        Product beforeUpdate = productDAO.getById(product.getId());
        LocalDateTime oldUpdatedAt = beforeUpdate.getUpdatedAt();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        beforeUpdate.setName("Updated Product");
        beforeUpdate.setPrice(250.0);
        beforeUpdate.setStockQuantity(35);
        boolean updateResult = productDAO.update(beforeUpdate);

        assertTrue(updateResult, "Cập nhật thất bại");

        Product afterUpdate = productDAO.getById(product.getId());
        assertEquals("Updated Product", afterUpdate.getName(), "Tên không được cập nhật");
        assertEquals(250.0, afterUpdate.getPrice(), 0.001, "Giá không được cập nhật");
        assertEquals(35L, (long) afterUpdate.getStockQuantity(), "Số lượng không được cập nhật");
        assertTrue(afterUpdate.getUpdatedAt().isAfter(oldUpdatedAt), "updatedAt không được cập nhật");
    }

    @Test
    public void testDeleteProduct() {
        Product product = createTestProduct("Product To Delete", 150.0, 20);

        boolean deleteResult = productDAO.delete(product.getId());

        assertTrue(deleteResult, "Xóa thất bại");

        Product afterDelete = productDAO.getById(product.getId());
        assertNull(afterDelete, "Product không bị xóa");
    }

    @Test
    public void testGetProductById() {
        Product product = createTestProduct("Test Get Product", 180.0, 25);

        Product retrievedProduct = productDAO.getById(product.getId());

        assertNotNull(retrievedProduct, "Không tìm thấy product");
        assertEquals(product.getId(), retrievedProduct.getId(), "ID không khớp");
        assertEquals("Test Get Product", retrievedProduct.getName(), "Tên không khớp");
        assertEquals(180.0, retrievedProduct.getPrice(), 0.001, "Giá không khớp");
        assertEquals(25L, (long) retrievedProduct.getStockQuantity(), "Số lượng không khớp");
    }

    @Test
    public void testGetAllProducts() {
        List<Product> productsBefore = productDAO.getAll();
        int countBefore = productsBefore.size();

        for (int i = 1; i <= 3; i++) {
            createTestProduct("Test Product " + i, 100.0 * i, 10 * i);
        }

        List<Product> productsAfter = productDAO.getAll();

        assertEquals(countBefore + 3, productsAfter.size(), "Số lượng product không tăng đúng");
    }

    @Test
    public void testGetProductsByCategory() {
        Category specialCategory = new Category();
        specialCategory.setName("Special Test Category");
        Integer specialCategoryId = categoryDAO.add(specialCategory);
        specialCategory.setId(specialCategoryId);

        for (int i = 1; i <= 2; i++) {
            createTestProduct("Default Category Product " + i, 100.0 * i, 10 * i);
        }

        for (int i = 1; i <= 3; i++) {
            Product product = new Product();
            product.setName("Special Category Product " + i);
            product.setPrice(50.0 * i);
            product.setStockQuantity(5 * i);
            product.setCategory(specialCategory);
            Integer productId = productDAO.add(product);
            product.setId(productId);
        }

        List<Product> specialProducts = productDAO.getByCategory(specialCategoryId);

        assertNotNull(specialProducts, "Danh sách product không được null");
        assertEquals(3, specialProducts.size(), "Số lượng product không đúng");

        for (Product product : specialProducts) {
            assertEquals(specialCategoryId, product.getCategory().getId(), "Category ID không khớp");
        }
    }

    private Product createTestProduct(String name, double price, int stockQuantity) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        product.setCategory(testCategory);

        Integer productId = productDAO.add(product);
        product.setId(productId);

        return product;
    }
}
