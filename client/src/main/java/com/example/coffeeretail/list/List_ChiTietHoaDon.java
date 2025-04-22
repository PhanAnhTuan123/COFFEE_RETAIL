package list;

import entity.OrderDetail;
import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;

import java.util.ArrayList;
import java.util.List;

public class List_ChiTietHoaDon {

	public List<OrderDetail> getAll() {
		try {
			Request req = new Request("orderDetail", "getAll", null);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), OrderDetail.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public Object get(String id) {
		try {
			Request req = new Request("orderDetail", "get", id);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJson(res.getData(), OrderDetail.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean save(OrderDetail detail) {
		try {
			Request req = new Request("orderDetail", "save", ProtocolUtils.toJson(detail));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(OrderDetail detail) {
		try {
			Request req = new Request("orderDetail", "update", ProtocolUtils.toJson(detail));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(OrderDetail detail) {
		try {
			Request req = new Request("orderDetail", "delete", ProtocolUtils.toJson(detail));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteById(String id) {
		try {
			Request req = new Request("orderDetail", "deleteById", id);
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<OrderDetail> getForCondition(String orderId) {
		try {
			Request req = new Request("orderDetail", "getForCondition", orderId);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), OrderDetail.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
