package list;

import entity.Customer;
import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;

import java.util.ArrayList;
import java.util.List;

public class List_KhachHang {

	public List<Customer> getAll() {
		try {
			Request req = new Request("customer", "getAll", null);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), Customer.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public Customer get(String id) {
		try {
			Request req = new Request("customer", "get", id);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJson(res.getData(), Customer.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean save(Customer customer) {
		try {
			String json = ProtocolUtils.toJson(customer);
			Request req = new Request("customer", "save", json);
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(Customer customer) {
		try {
			String json = ProtocolUtils.toJson(customer);
			Request req = new Request("customer", "update", json);
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Customer customer) {
		try {
			String json = ProtocolUtils.toJson(customer);
			Request req = new Request("customer", "delete", json);
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Customer> findByName(String name) {
		try {
			Request req = new Request("customer", "findByName", name);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), Customer.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
