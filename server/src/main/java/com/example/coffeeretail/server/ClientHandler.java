package com.example.coffeeretail.server;

import com.example.coffeeretail.dto.Request;
import com.example.coffeeretail.dto.Response;
import com.example.coffeeretail.dto.Credentials;
import com.example.coffeeretail.dto.OrderRequest;
import com.example.coffeeretail.entity.*;
import com.example.coffeeretail.protocol.ProtocolUtils;
import com.example.coffeeretail.service.CategoryService;
import com.example.coffeeretail.service.ProductService;
import com.example.coffeeretail.service.TableService;
import com.example.coffeeretail.service.CustomerService;
import com.example.coffeeretail.service.EmployeeService;
import com.example.coffeeretail.service.EmployeeDetailService;
import com.example.coffeeretail.service.AccountService;
import com.example.coffeeretail.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ObjectMapper mapper = new ObjectMapper();


    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
//        new OrderService().create(reqData.getUserId(), reqData.getDetails());

        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {

            while (true) {
                Request req = ProtocolUtils.readRequest(in);
                if (req == null) break;

                Response resp = dispatch(req);
                ProtocolUtils.writeResponse(out, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response dispatch(Request req) {
        String action  = req.getAction();
        String payload = req.getPayload();

        try {
            switch (action) {
                // ===== CATEGORY =====
                case "GET_CATEGORIES": {
                    List<Category> list = new CategoryService().list();
                    return Response.okWithData(list);
                }
                case "CREATE_CATEGORY": {
                    Category c = mapper.readValue(payload, Category.class);
                    Category created = new CategoryService()
                            .create(c.getId(), c.getName(), c.getDescription());
                    return Response.okWithData(created);
                }
                case "UPDATE_CATEGORY": {
                    Category c = mapper.readValue(payload, Category.class);
                    Category updated = new CategoryService()
                            .update(c.getId(), c.getName(), c.getDescription());
                    return Response.okWithData(updated);
                }
                case "DELETE_CATEGORY": {
                    String id = mapper.readValue(payload, String.class);
                    new CategoryService().delete(id);
                    return Response.okNoData();
                }

                // ===== PRODUCT =====
                case "GET_PRODUCTS": {
                    List<Product> list = new ProductService().list();
                    return Response.okWithData(list);
                }
                case "CREATE_PRODUCT": {
                    Product p = mapper.readValue(payload, Product.class);
                    Product created = new ProductService()
                            .create(p.getId(), p.getName(), p.getPrice(), p.getCategory().getId());
                    return Response.okWithData(created);
                }
                case "UPDATE_PRODUCT": {
                    Product p = mapper.readValue(payload, Product.class);
                    Product updated = new ProductService()
                            .update(p.getId(), p.getName(), p.getPrice(), p.getCategory().getId());
                    return Response.okWithData(updated);
                }
                case "DELETE_PRODUCT": {
                    String id = mapper.readValue(payload, String.class);
                    new ProductService().delete(id);
                    return Response.okNoData();
                }

                // ===== TABLES =====
                case "GET_TABLES": {
                    List<Tables> list = new TableService().list();
                    return Response.okWithData(list);
                }
                case "CREATE_TABLE": {
                    Tables t = mapper.readValue(payload, Tables.class);
                    Tables created = new TableService()
                            .create(t);
                    return Response.okWithData(created);
                }
                case "UPDATE_TABLE": {
                    Tables t = mapper.readValue(payload, Tables.class);
                    Tables updated = new TableService()
                            .update(t);
                    return Response.okWithData(updated);
                }
                case "DELETE_TABLE": {
                    String id = mapper.readValue(payload, String.class);
                    new TableService().delete(id);
                    return Response.okNoData();
                }

                // ===== CUSTOMER =====
                case "GET_CUSTOMERS": {
                    List<Customer> list = new CustomerService().list();
                    return Response.okWithData(list);
                }
                case "CREATE_CUSTOMER": {
                    Customer c = mapper.readValue(payload, Customer.class);
                    Customer created = new CustomerService()
                            .create(c);
                    return Response.okWithData(created);
                }
                case "UPDATE_CUSTOMER": {
                    Customer c = mapper.readValue(payload, Customer.class);
                    Customer updated = new CustomerService()
                            .update(c);
                    return Response.okWithData(updated);
                }
                case "DELETE_CUSTOMER": {
                    String id = mapper.readValue(payload, String.class);
                    new CustomerService().delete(id);
                    return Response.okNoData();
                }

                // ===== EMPLOYEE =====
                case "GET_EMPLOYEES": {
                    List<Employee> list = new EmployeeService().list();
                    return Response.okWithData(list);
                }
                case "CREATE_EMPLOYEE": {
                    Employee e = mapper.readValue(payload, Employee.class);
                    Employee created = new EmployeeService()
                            .create(e);
                    return Response.okWithData(created);
                }
                case "UPDATE_EMPLOYEE": {
                    Employee e = mapper.readValue(payload, Employee.class);
                    Employee updated = new EmployeeService()
                            .update(e);
                    return Response.okWithData(updated);
                }
                case "DELETE_EMPLOYEE": {
                    String id = mapper.readValue(payload, String.class);
                    new EmployeeService().delete(id);
                    return Response.okNoData();
                }

                // ===== EMPLOYEE DETAIL =====
                case "GET_EMPLOYEE_DETAILS": {
                    List<EmployeeDetail> list = new EmployeeDetailService().list();
                    return Response.okWithData(list);
                }
                case "CREATE_EMPLOYEE_DETAIL": {
                    EmployeeDetail d = mapper.readValue(payload, EmployeeDetail.class);
                    EmployeeDetail created = new EmployeeDetailService()
                            .create(d);
                    return Response.okWithData(created);
                }
                case "UPDATE_EMPLOYEE_DETAIL": {
                    EmployeeDetail d = mapper.readValue(payload, EmployeeDetail.class);
                    EmployeeDetail updated = new EmployeeDetailService()
                            .update(d);
                    return Response.okWithData(updated);
                }
                case "DELETE_EMPLOYEE_DETAIL": {
                    String id = mapper.readValue(payload, String.class);
                    new EmployeeDetailService().delete(id);
                    return Response.okNoData();
                }

                // ===== AUTH =====
                case "LOGIN": {
                    Credentials cred = mapper.readValue(payload, Credentials.class);
                    Account account = new AccountService()
                            .authenticate(cred.getUsername(), cred.getPassword());
                    return Response.okWithData(account);
                }

                // ===== ORDER =====
                case "GET_ORDERS": {
                    List<Order> list = new OrderService().list();
                    return Response.okWithData(list);
                }
                case "CREATE_ORDER": {
                    // 1. đọc payload thành OrderRequest
                    OrderRequest reqData = mapper.readValue(payload, OrderRequest.class);

                    // 2. lấy ra Order và details
                    Order o       = reqData.getOrder();
                    List<OrderDetail> details = reqData.getDetails();

                    // 3. call đúng chữ ký
                    Order created = new OrderService().create(o, details);
                    return Response.okWithData(created);
                }


                default:
                    return Response.error("Unknown action: " + action);
            }
        } catch (Exception ex) {
            return Response.error("Error on " + action + ": " + ex.getMessage());
        }
    }
}
