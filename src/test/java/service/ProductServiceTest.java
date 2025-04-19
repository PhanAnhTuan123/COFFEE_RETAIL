package service;

import java.entity.Product;
import java.entity.Category;
import java.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1);
        product.setName("Trà đào");
        product.setPrice(25000.0);
        product.setStockQuantity(100);
        product.setCategory(new Category("Đồ uống"));
    }

    @Test
    public void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product created = productService.createProduct(product);
        assertNotNull(created);
        assertEquals("Trà đào", created.getName());
    }

    @Test
    public void testGetProductById() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        Optional<Product> fetched = productService.getProductById(1);
        assertTrue(fetched.isPresent());
        assertEquals(25000.0, fetched.get().getPrice());
    }

    @Test
    public void testUpdateProduct() {
        Product updatedDetails = new Product();
        updatedDetails.setName("Trà đào cam sả");
        updatedDetails.setPrice(30000.0);
        updatedDetails.setStockQuantity(80);
        updatedDetails.setCategory(new Category("Đồ uống"));

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName("Trà đào cam sả");
        updatedProduct.setPrice(30000.0);
        updatedProduct.setStockQuantity(80);
        updatedProduct.setCategory(new Category("Đồ uống"));

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(1, updatedDetails);
        assertEquals("Trà đào cam sả", result.getName());
        assertEquals(80, result.getStockQuantity());
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1);
        productService.deleteProduct(1);
        verify(productRepository, times(1)).deleteById(1);
    }
}