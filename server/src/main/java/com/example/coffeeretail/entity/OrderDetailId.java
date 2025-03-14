package com.example.coffeeretail.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderDetailId implements Serializable {
    private String orderId;
    private String productId;

    public OrderDetailId() { }

    public OrderDetailId(String orderId, String productId) {
        this.orderId   = orderId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailId)) return false;
        OrderDetailId that = (OrderDetailId) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
