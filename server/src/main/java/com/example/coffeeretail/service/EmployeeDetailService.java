package com.example.coffeeretail.service;

import com.example.coffeeretail.dao.EmployeeDetailDAO;
import com.example.coffeeretail.entity.EmployeeDetail;
import java.util.List;

public class EmployeeDetailService {
    private final EmployeeDetailDAO dao = new EmployeeDetailDAO();

    public EmployeeDetail create(EmployeeDetail d) {
        dao.save(d);
        return d;
    }
    public EmployeeDetail get(String id) {
        return dao.findById(id);
    }
    public List<EmployeeDetail> list() {
        return dao.findAll();
    }
    public EmployeeDetail update(EmployeeDetail d) {
        return dao.update(d);
    }
    public void delete(String id) {
        dao.delete(id);
    }
}
