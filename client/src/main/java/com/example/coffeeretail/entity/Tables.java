package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter

public class Tables implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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
