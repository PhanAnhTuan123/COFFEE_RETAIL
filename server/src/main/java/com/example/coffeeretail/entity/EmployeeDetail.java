package com.example.coffeeretail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "EmployeeDetail")
public class EmployeeDetail {
    @Id
    @Column(name = "maNV", length = 50)
    private String maNV;

    @Column(name = "tongSoCaLam")
    private Integer tongSoCaLam;

    @Column(name = "luong")
    private Double luong;

    // getters & setters
//    public String getMaNV() { return maNV; }
//    public void setMaNV(String maNV) { this.maNV = maNV; }
//    public Integer getTongSoCaLam() { return tongSoCaLam; }
//    public void setTongSoCaLam(Integer tongSoCaLam) { this.tongSoCaLam = tongSoCaLam; }
//    public Double getLuong() { return luong; }
//    public void setLuong(Double luong) { this.luong = luong; }
}