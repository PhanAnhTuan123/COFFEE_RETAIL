package entity;

import jakarta.persistence.*;
import entity.Category;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "maHH", length = 50, nullable = false)
    private String id;

    @Column(name = "TenHH", length = 100, nullable = false)
    private String name;

    @Column(name = "Gia", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maCategory")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // Custom constructor
    public Product(String id, String name, Double price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }


//    public Product() { }
//
//    public Product(String id, String name, Double price, Category category) {
//        this.id       = id;
//        this.name     = name;
//        this.price    = price;
//        this.category = category;
//    }
//
//    public String getId() {
//        return id;
//    }
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Double getPrice() {
//        return price;
//    }
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//
//    public Category getCategory() {
//        return category;
//    }
//    public void setCategory(Category category) {
//        this.category = category;
//    }
//
//    public List<OrderDetail> getOrderDetails() {
//        return orderDetails;
//    }
//    public void setOrderDetails(List<OrderDetail> orderDetails) {
//        this.orderDetails = orderDetails;
//    }
}
