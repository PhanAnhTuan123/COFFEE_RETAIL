package entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "maCategory", length = 50, nullable = false)
    private String id;

    @Column(name = "tenCategory", length = 100)
    private String name;

    @Column(name = "moTa", length = 255)
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public Category() { }

    public Category(String id, String name, String description) {
        this.id          = id;
        this.name        = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product p) {
        products.add(p);
        p.setCategory(this);
    }

    public void removeProduct(Product p) {
        products.remove(p);
        p.setCategory(null);
    }
}
