package com.example.coffeeretail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Account")
public class Account {
    @Id
    @Column(name = "userName", length = 50)
    private String userName;

    @Column(name = "passWord", length = 100)
    private String passWord;

    // getters & setters
//    public String getUserName() { return userName; }
//    public void setUserName(String userName) { this.userName = userName; }
//    public String getPassWord() { return passWord; }
//    public void setPassWord(String passWord) { this.passWord = passWord; }
}
