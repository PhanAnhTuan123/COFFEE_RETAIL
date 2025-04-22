package list;

import entity.EmployeeDetail;
import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;

import java.util.ArrayList;
import java.util.List;

public class List_ChiTietNhanVien {

	public List<EmployeeDetail> getAll() {
		try {
			Request req = new Request("employeeDetail", "getAll", null);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), EmployeeDetail.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public EmployeeDetail get(String id) {
		try {
			Request req = new Request("employeeDetail", "get", id);
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJson(res.getData(), EmployeeDetail.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean add(EmployeeDetail detail) {
		try {
			Request req = new Request("employeeDetail", "save", ProtocolUtils.toJson(detail));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(EmployeeDetail detail) {
		try {
			Request req = new Request("employeeDetail", "update", ProtocolUtils.toJson(detail));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean remove(EmployeeDetail detail) {
		try {
			Request req = new Request("employeeDetail", "delete", ProtocolUtils.toJson(detail));
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean removeById(String id) {
		try {
			Request req = new Request("employeeDetail", "deleteById", id);
			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
