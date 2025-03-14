package list;

import constraint.CRUD;
import model.ChiTietHoaDon;
import service.ChiTietHoaDon_DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class List_ChiTietHoaDon implements CRUD<ChiTietHoaDon>{

	@Override
	public ArrayList<ChiTietHoaDon> getAll() throws SQLException {
		return new ChiTietHoaDon_DAO().getAll();
	}

	@Override
	public ChiTietHoaDon get(String id) throws SQLException {
		return new ChiTietHoaDon_DAO().get(id);
	}

	@Override
	public void save(ChiTietHoaDon t) throws SQLException {
		new ChiTietHoaDon_DAO().save(t);
	}

	@Override
	public void update(ChiTietHoaDon t) throws SQLException {
		new ChiTietHoaDon_DAO().update(t);
	}

	@Override
	public void delete(ChiTietHoaDon t) throws SQLException {
		new ChiTietHoaDon_DAO().delete(t);
		
		
	}

	@Override
	public void deleteById(int id) throws SQLException {
		new ChiTietHoaDon_DAO().deleteById(id);
	}
	public ArrayList<ChiTietHoaDon>getForCondition(String maHD){
		return new ChiTietHoaDon_DAO().getForCondition(maHD);
	}

}
