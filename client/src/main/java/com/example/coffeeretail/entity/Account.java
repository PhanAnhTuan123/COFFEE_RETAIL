package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "userName", length = 50)
    private String userName;

    @Column(name = "passWord", length = 100)
    private String passWord;

    @Column(name = "role", length = 50)
    private String role; // Thêm field role

    // Bổ sung phương thức getter
    public String getUsername() {
        return userName;
    }

    public String getRole() {
        return role;
    }
}
