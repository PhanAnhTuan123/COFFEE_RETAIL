package list;

import constraint.CRUD;
import model.TaiKhoan;
import service.TaiKhoan_DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class List_TaiKhoan implements CRUD<TaiKhoan> {
	private TaiKhoan_DAO tk_dao;

	public List_TaiKhoan() {
		tk_dao = new TaiKhoan_DAO();
	}

	@Override
	public ArrayList<TaiKhoan> getAll() throws SQLException {
		return tk_dao.getAll();
	}

	@Override
	public TaiKhoan get(String id) throws SQLException {
		return tk_dao.get(id);
	}

	@Override
	public void save(TaiKhoan t) throws SQLException {
		tk_dao.save(t);
	}

	@Override
	public void update(TaiKhoan t) throws SQLException {
		tk_dao.update(t);
	}

	@Override
	public void delete(TaiKhoan t) throws SQLException {
		tk_dao.delete(t);
	}

	@Override
	public void deleteById(int id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<TaiKhoan>findByName(String name){
		return tk_dao.findByName(name);
	}
	
	
}
