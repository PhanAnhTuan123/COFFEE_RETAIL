package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "maKH", length = 50)
    private String maKH;

    @Column(name = "tenKH", length = 200)
    private String tenKH;

    @Column(name = "diaChi", length = 255)
    private String diaChi;

    @Column(name = "sdt", length = 20)
    private String sdt;
}