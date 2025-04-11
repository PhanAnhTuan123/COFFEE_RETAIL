package list;

import constraint.CRUD;
import model.NhanVien;
import service.NhanVien_DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class List_NhanVien implements CRUD<NhanVien>{
	
private NhanVien_DAO nv_dao;
	
	public List_NhanVien() {
		nv_dao = new NhanVien_DAO();
	}
	
	@Override
	public ArrayList<NhanVien> getAll() throws SQLException {
		return nv_dao.getAll();
	}

	@Override
	public NhanVien get(String id) throws SQLException {
		return nv_dao.get(id);
	}

	@Override
	public void save(NhanVien t) throws SQLException {
		nv_dao.save(t);
	}

	@Override
	public void update(NhanVien t) throws SQLException {
		nv_dao.update(t);
	}

	@Override
	public void delete(NhanVien t) throws SQLException {
		nv_dao.delete(t);
	}

	@Override
	public void deleteById(int id) throws SQLException {
		nv_dao.deleteById(id);
	}
	
	public String sinhMaNV() {
		return nv_dao.sinhMaNV();
	}
	
	public ArrayList<NhanVien>findByName(String name){
		return nv_dao.findByName(name);
	}	
	
	public String getTenNV(String id) throws SQLException {
		return nv_dao.getTenNV(id);
	}
}
