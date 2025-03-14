package list;

import constraint.CRUD;
import model.KhachHang;
import service.KhachHang_DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class List_KhachHang implements CRUD<KhachHang>{
private KhachHang_DAO kh_dao;
	
	public List_KhachHang() {
		kh_dao = new KhachHang_DAO();
	}
	
	@Override
	public ArrayList<KhachHang> getAll() throws SQLException {
		return kh_dao.getAll();
	}

	@Override
	public KhachHang get(String id) throws SQLException {
		return kh_dao.get(id);
	}

	@Override
	public void save(KhachHang t) throws SQLException {
		kh_dao.save(t);
	}

	@Override
	public void update(KhachHang t) throws SQLException {
		kh_dao.update(t);
	}

	@Override
	public void delete(KhachHang t) throws SQLException {
		kh_dao.delete(t);
	}

	@Override
	public void deleteById(int id) throws SQLException {
		kh_dao.deleteById(id);
	}
	
	public String sinhMaKH() {
		return kh_dao.sinhMaKH();
	}
	
	public ArrayList<KhachHang>findByName(String name){
		return kh_dao.findByName(name);
	}	
}
