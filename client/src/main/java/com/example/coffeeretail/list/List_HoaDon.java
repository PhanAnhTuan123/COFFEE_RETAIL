package list;

import entity.Order;
import org.jfree.data.category.DefaultCategoryDataset;
import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;

import java.util.ArrayList;
import java.util.List;

public class List_HoaDon {

	public List<Order> getAll() {
		try {
			Request req = new Request("order", "getAll", null);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), Order.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public Order get(String id) {
		try {
			Request req = new Request("order", "get", id);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJson(res.getData(), Order.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean save(Order order) {
		try {
			String json = ProtocolUtils.toJson(order);
			Request req = new Request("order", "save", json);
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(Order order) {
		try {
			String json = ProtocolUtils.toJson(order);
			Request req = new Request("order", "update", json);
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Order order) {
		try {
			String json = ProtocolUtils.toJson(order);
			Request req = new Request("order", "delete", json);
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<String[]> getThongTinDoanhThu() {
		try {
			Request req = new Request("order", "thongkeDoanhThu", null);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), String[].class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public String getThongKeDoanhThuCaoNhatTrongNam() {
		try {
			Request req = new Request("order", "doanhThuMaxTrongNam", null);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return res.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public DefaultCategoryDataset drawThongKeDoanhThuCaoNhatTrongNam() {
		try {
			Request req = new Request("order", "drawDoanhThuMaxChart", null);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				// Custom deserialize if you sent dataset as serialized JSON format from server
				return ProtocolUtils.fromJson(res.getData(), DefaultCategoryDataset.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultCategoryDataset();
	}

	// New method to generate a unique order ID
	public String sinhMa() {
		try {
			List<Order> orders = getAll();
			int maxId = 0;

			for (Order order : orders) {
				String id = order.getId(); // Assuming Order has a getId() method
				if (id.startsWith("HD")) {
					try {
						int numericPart = Integer.parseInt(id.substring(2));
						if (numericPart > maxId) {
							maxId = numericPart;
						}
					} catch (NumberFormatException ignored) {
						// Ignore invalid IDs
					}
				}
			}

			// Generate the next ID
			return "HD" + String.format("%03d", maxId + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "HD001"; // Default ID if something goes wrong
	}
}