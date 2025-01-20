package com.example.cafenetworksolution.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Auth")
@Getter
@Setter
public class Auth {

    @Id
    private Integer userID;

    @OneToOne
    @JoinColumn(name = "userID", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

}

