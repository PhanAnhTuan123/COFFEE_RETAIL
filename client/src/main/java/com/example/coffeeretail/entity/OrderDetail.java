package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderDetailId.class)
public class OrderDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "maHD", length = 50, nullable = false)
    private String orderId;

    @Id
    @Column(name = "maHH", length = 50, nullable = false)
    private String productId;

    @Column(name = "soLuong", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maHD", insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maHH", insertable = false, updatable = false)
    private Product product;

    // Custom constructor
    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.productId = product.getId();
        this.quantity = quantity;
        this.subtotal = product.getPrice() * quantity;
    }

    // Constructor to match (String, String, int, double)
    public OrderDetail(String orderId, String productId, int quantity, double subtotal) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }
}