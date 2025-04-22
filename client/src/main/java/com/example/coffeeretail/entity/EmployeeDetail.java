package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter

public class EmployeeDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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