package list;

import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;
import entity.Account;

import java.util.List;

public class List_TaiKhoan {

	public Account get(String id) {
		try {
			Request req = new Request();
			req.setResource("account");
			req.setAction("get");
			req.setData(id);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJson(res.getData(), Account.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Account> getAll() {
		try {
			Request req = new Request();
			req.setResource("account");
			req.setAction("getAll");
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), Account.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean save(Account acc) {
		try {
			Request req = new Request();
			req.setResource("account");
			req.setAction("save");
			req.setData(ProtocolUtils.toJson(acc));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(Account acc) {
		try {
			Request req = new Request();
			req.setResource("account");
			req.setAction("update");
			req.setData(ProtocolUtils.toJson(acc));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Account acc) {
		try {
			Request req = new Request();
			req.setResource("account");
			req.setAction("delete");
			req.setData(ProtocolUtils.toJson(acc));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Account> findByName(String name) {
		try {
			Request req = new Request();
			req.setResource("account");
			req.setAction("findByName");
			req.setData(name);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), Account.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
