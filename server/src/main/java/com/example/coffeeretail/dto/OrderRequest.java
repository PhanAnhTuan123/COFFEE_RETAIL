package com.example.coffeeretail.dto;

import com.example.coffeeretail.entity.Order;
import com.example.coffeeretail.entity.OrderDetail;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class OrderRequest implements Serializable {
    private Order order;
    private List<OrderDetail> details;

//    public OrderRequest() { }
//
//    public OrderRequest(Order order, List<OrderDetail> details) {
//        this.order   = order;
//        this.details = details;
//    }
//
//    public Order getOrder() {
//        return order;
//    }
//    public void setOrder(Order order) {
//        this.order = order;
//    }
//
//    public List<OrderDetail> getDetails() {
//        return details;
//    }
//    public void setDetails(List<OrderDetail> details) {
//        this.details = details;
//    }
}
