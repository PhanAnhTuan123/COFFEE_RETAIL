package com.example.coffeeretail.service;

import com.example.coffeeretail.dao.CategoryDAO;
import com.example.coffeeretail.entity.Category;
import java.util.List;

public class CategoryService {
    private final CategoryDAO dao = new CategoryDAO();

    public Category create(String id, String name, String description) {
        Category c = new Category(id, name, description);
        dao.save(c);
        return c;
    }

    public Category get(String id) {
        return dao.findById(id);
    }

    public List<Category> list() {
        return dao.findAll();
    }

    public Category update(String id, String name, String description) {
        Category c = dao.findById(id);
        if (c == null) throw new RuntimeException("Category not found");
        c.setName(name);
        c.setDescription(description);
        return dao.update(c);
    }

    public void delete(String id) {
        dao.delete(id);
    }
}
