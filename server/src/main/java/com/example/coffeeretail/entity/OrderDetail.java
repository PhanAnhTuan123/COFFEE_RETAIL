package com.example.coffeeretail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OrderDetail")
@IdClass(OrderDetailId.class)
public class OrderDetail implements Serializable {
    @Id
    @Column(name = "maHD",   length = 50, nullable = false)
    private String orderId;

    @Id
    @Column(name = "maHH",   length = 50, nullable = false)
    private String productId;

    @Column(name = "soLuong", nullable = false)
    private Integer quantity;

    @Column(name = "price",   nullable = false)
    private Double subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maHD", insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maHH", insertable = false, updatable = false)
    private Product product;





//    public OrderDetail() { }
//
//    public OrderDetail(String orderId, String productId, Integer quantity, Double subtotal) {
//        this.orderId   = orderId;
//        this.productId = productId;
//        this.quantity  = quantity;
//        this.subtotal  = subtotal;
//    }
//
//    public String getOrderId() {
//        return orderId;
//    }
//    public void setOrderId(String orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getProductId() {
//        return productId;
//    }
//    public void setProductId(String productId) {
//        this.productId = productId;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public Double getSubtotal() {
//        return subtotal;
//    }
//    public void setSubtotal(Double subtotal) {
//        this.subtotal = subtotal;
//    }
//
//    public Order getOrder() {
//        return order;
//    }
//    public void setOrder(Order order) {
//        this.order   = order;
//        this.orderId = order != null ? order.getId() : null;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//    public void setProduct(Product product) {
//        this.product    = product;
//        this.productId  = product != null ? product.getId() : null;
//    }
}
