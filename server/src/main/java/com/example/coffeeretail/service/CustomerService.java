package com.example.coffeeretail.service;

import com.example.coffeeretail.dao.CustomerDAO;
import com.example.coffeeretail.entity.Customer;
import java.util.List;

public class CustomerService {
    private final CustomerDAO dao = new CustomerDAO();

    public Customer create(Customer c) {
        dao.save(c);
        return c;
    }
    public Customer get(String id) {
        return dao.findById(id);
    }
    public List<Customer> list() {
        return dao.findAll();
    }
    public Customer update(Customer c) {
        return dao.update(c);
    }
    public void delete(String id) {
        dao.delete(id);
    }
}
