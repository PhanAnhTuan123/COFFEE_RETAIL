package com.example.coffeeretail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Customer")
public class Customer {
    @Id
    @Column(name = "maKH", length = 50)
    private String maKH;

    @Column(name = "tenKH", length = 200)
    private String tenKH;

    @Column(name = "diaChi", length = 255)
    private String diaChi;

    @Column(name = "sdt", length = 20)
    private String sdt;

    // getters & setters
//    public String getMaKH() { return maKH; }
//    public void setMaKH(String maKH) { this.maKH = maKH; }
//    public String getTenKH() { return tenKH; }
//    public void setTenKH(String tenKH) { this.tenKH = tenKH; }
//    public String getDiaChi() { return diaChi; }
//    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
//    public String getSdt() { return sdt; }
//    public void setSdt(String sdt) { this.sdt = sdt; }
}