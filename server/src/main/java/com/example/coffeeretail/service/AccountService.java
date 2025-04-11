package com.example.coffeeretail.service;

import com.example.coffeeretail.dao.AccountDAO;
import com.example.coffeeretail.entity.Account;

public class AccountService {
    private final AccountDAO dao = new AccountDAO();

    public Account authenticate(String username, String password) {
        Account a = dao.findByUsername(username);
        if (a != null && a.getPassWord().equals(password)) return a;
        throw new RuntimeException("Invalid credentials");
    }

    public Account create(Account a) {
        dao.save(a);
        return a;
    }
    public void delete(String username) {
        dao.delete(username);
    }
}
