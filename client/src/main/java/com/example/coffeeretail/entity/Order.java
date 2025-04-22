package entity;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "maHD", length = 50, nullable = false)
    private String id;

    @Column(name = "ngay", nullable = false)
    private LocalDateTime date;

    @Column(name = "tongTien", nullable = false)
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKH", nullable = false)
    private Customer khachHang;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> details = new ArrayList<>();

    public Order() { }

    public Order(String id, LocalDateTime date, Double totalPrice, Customer khachHang) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.khachHang = khachHang;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getKhachHang() {
        return khachHang;
    }
    public void setKhachHang(Customer khachHang) {
        this.khachHang = khachHang;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }
    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public void addDetail(OrderDetail d) {
        details.add(d);
        d.setOrder(this);
    }
    public void removeDetail(OrderDetail d) {
        details.remove(d);
        d.setOrder(null);
    }
}