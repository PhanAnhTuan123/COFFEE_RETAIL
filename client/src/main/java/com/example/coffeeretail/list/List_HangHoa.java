package list;

import entity.Product;
import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;

import java.util.ArrayList;
import java.util.List;

public class List_HangHoa {

	public List<Product> getAll() {
		try {
			Request req = new Request("product", "getAll", null);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), Product.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public Product get(String id) {
		try {
			Request req = new Request("product", "get", id);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJson(res.getData(), Product.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean add(Product hh) {
		try {
			Request req = new Request("product", "save", ProtocolUtils.toJson(hh));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(Product hh) {
		try {
			Request req = new Request("product", "update", ProtocolUtils.toJson(hh));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean remove(Product hh) {
		try {
			Request req = new Request("product", "delete", ProtocolUtils.toJson(hh));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean removeById(String id) {
		try {
			Request req = new Request("product", "deleteById", id);
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String sinhMaHH() {
		try {
			Request req = new Request("product", "generateId", null);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return res.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public List<Product> findByName(String name) {
		try {
			Request req = new Request("product", "findByName", name);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), Product.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public Product getHangHoaForID(String id) {
		return get(id); // tái sử dụng hàm get()
	}
}
