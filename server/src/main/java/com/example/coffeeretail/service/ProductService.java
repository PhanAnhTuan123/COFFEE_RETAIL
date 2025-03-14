package com.example.coffeeretail.service;

import com.example.coffeeretail.dao.ProductDAO;
import com.example.coffeeretail.entity.Category;
import com.example.coffeeretail.entity.Product;
import java.util.List;

public class ProductService {
    private final ProductDAO dao = new ProductDAO();
    private final CategoryService categoryService = new CategoryService();

    public Product create(String id, String name, Double price, String categoryId) {
        Category cat = categoryService.get(categoryId);
        if (cat == null) throw new RuntimeException("Category not found");

        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setPrice(price);
        p.setCategory(cat);

        dao.save(p);
        return p;
    }


    public Product get(String id) {
        return dao.findById(id);
    }

    public List<Product> list() {
        return dao.findAll();
    }

    public Product update(String id, String name, Double price, String categoryId) {
        Product p = dao.findById(id);
        if (p == null) throw new RuntimeException("Product not found");
        Category cat = categoryService.get(categoryId);
        if (cat == null) throw new RuntimeException("Category not found");
        p.setName(name);
        p.setPrice(price);
        p.setCategory(cat);
        return dao.update(p);
    }

    public void delete(String id) {
        dao.delete(id);
    }
}
