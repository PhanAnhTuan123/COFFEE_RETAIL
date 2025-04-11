package com.example.coffeeretail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name = "maNV", length = 50)
    private String maNV;

    @Column(name = "tenNV", length = 200)
    private String tenNV;

    @Column(name = "diaChi", length = 255)
    private String diaChi;

    @Column(name = "sdt", length = 20)
    private String sdt;

    @Column(name = "chucVu", length = 100)
    private String chucVu;

    @Column(name = "gioiTinh")
    private Boolean gioiTinh;

    // getters & setters
//    public String getMaNV() { return maNV; }
//    public void setMaNV(String maNV) { this.maNV = maNV; }
//    public String getTenNV() { return tenNV; }
//    public void setTenNV(String tenNV) { this.tenNV = tenNV; }
//    public String getDiaChi() { return diaChi; }
//    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
//    public String getSdt() { return sdt; }
//    public void setSdt(String sdt) { this.sdt = sdt; }
//    public String getChucVu() { return chucVu; }
//    public void setChucVu(String chucVu) { this.chucVu = chucVu; }
//    public Boolean getGioiTinh() { return gioiTinh; }
//    public void setGioiTinh(Boolean gioiTinh) { this.gioiTinh = gioiTinh; }
}