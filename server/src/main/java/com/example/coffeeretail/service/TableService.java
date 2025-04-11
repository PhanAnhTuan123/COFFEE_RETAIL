package com.example.coffeeretail.service;

import com.example.coffeeretail.dao.TableDAO;
import com.example.coffeeretail.entity.Tables;
import java.util.List;

public class TableService {
    private final TableDAO dao = new TableDAO();

    public Tables create(Tables t) {
        dao.save(t);
        return t;
    }
    public Tables get(String id) {
        return dao.findById(id);
    }
    public List<Tables> list() {
        return dao.findAll();
    }
    public Tables update(Tables t) {
        return dao.update(t);
    }
    public void delete(String id) {
        dao.delete(id);
    }
}
