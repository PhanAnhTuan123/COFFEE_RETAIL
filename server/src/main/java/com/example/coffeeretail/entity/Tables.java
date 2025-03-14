package com.example.coffeeretail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Table")
public class Tables {
    @Id
    @Column(name = "maBan", length = 50)
    private String maBan;

    @Column(name = "tenBan", length = 100)
    private String tenBan;

    // getters & setters
//    public String getMaBan() { return maBan; }
//    public void setMaBan(String maBan) { this.maBan = maBan; }
//    public String getTenBan() { return tenBan; }
//    public void setTenBan(String tenBan) { this.tenBan = tenBan; }
}
