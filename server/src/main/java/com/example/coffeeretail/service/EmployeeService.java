package com.example.coffeeretail.service;

import com.example.coffeeretail.dao.EmployeeDAO;
import com.example.coffeeretail.entity.Employee;
import java.util.List;

public class EmployeeService {
    private final EmployeeDAO dao = new EmployeeDAO();

    public Employee create(Employee e) {
        dao.save(e);
        return e;
    }
    public Employee get(String id) {
        return dao.findById(id);
    }
    public List<Employee> list() {
        return dao.findAll();
    }
    public Employee update(Employee e) {
        return dao.update(e);
    }
    public void delete(String id) {
        dao.delete(id);
    }
}
