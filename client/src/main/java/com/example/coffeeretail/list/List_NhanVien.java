package list;

import entity.Employee;
import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;

import java.util.ArrayList;
import java.util.List;

public class List_NhanVien {

	public List<Employee> getAll() {
		try {
			Request req = new Request();
			req.setAction("getAll");
			req.setData("nhanvien");

			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), Employee.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public Employee get(String id) {
		try {
			Request req = new Request();
			req.setAction("get");
			req.setData(id);  // id sẽ là mã nhân viên

			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJson(res.getData(), Employee.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean save(Employee nv) {
		try {
			Request req = new Request();
			req.setAction("save");
			req.setData(ProtocolUtils.toJson(nv));

			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(Employee nv) {
		try {
			Request req = new Request();
			req.setAction("update");
			req.setData(ProtocolUtils.toJson(nv));

			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Employee nv) {
		try {
			Request req = new Request();
			req.setAction("delete");
			req.setData(ProtocolUtils.toJson(nv));

			Response res = ClientSocket.sendRequest(req);
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Employee> findByName(String name) {
		try {
			Request req = new Request();
			req.setAction("findByName");
			req.setData(name);

			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return ProtocolUtils.fromJsonList(res.getData(), Employee.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public String getTenNV(String maNV) {
		try {
			Request req = new Request();
			req.setAction("getTenNV");
			req.setData(maNV);

			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				return res.getData(); // server chỉ trả về chuỗi tên nhân viên
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
