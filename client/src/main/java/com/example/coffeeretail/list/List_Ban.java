package list;

import entity.Tables;
import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;

import java.util.ArrayList;
import java.util.List;

public class List_Ban {

	public List<Tables> getAll() {
		try {
			Request req = new Request("table", "getAll", null);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), Tables.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public Tables get(String id) {
		try {
			Request req = new Request("tables", "get", id);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJson(res.getData(), Tables.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean save(Tables t) {
		try {
			Request req = new Request("table", "save", ProtocolUtils.toJson(t));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(Tables t) {
		try {
			Request req = new Request("table", "update", ProtocolUtils.toJson(t));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Tables t) {
		try {
			Request req = new Request("table", "delete", ProtocolUtils.toJson(t));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteById(String id) {
		try {
			Request req = new Request("table", "deleteById", id);
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Tables> findByName(String name) {
		try {
			Request req = new Request("table", "findByName", name);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), Tables.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public Tables getBanById(String id) {
		try {
			Request req = new Request("table", "getBanById", id);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJson(res.getData(), Tables.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}