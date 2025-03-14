package list;

import constraint.CRUD;
import model.Ban;
import service.Ban_DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class List_Ban implements CRUD<Ban>{

	@Override
	public ArrayList<Ban> getAll() throws SQLException {
		return new Ban_DAO().getAll();
	}

	@Override
	public Ban get(String id) throws SQLException {
		return new Ban_DAO().get(id);
	}

	@Override
	public void save(Ban t) throws SQLException {
		new Ban_DAO().save(t);
	}

	@Override
	public void update(Ban t) throws SQLException {
		new Ban_DAO().update(t);
	}

	@Override
	public void delete(Ban t) throws SQLException {
		new Ban_DAO().delete(t);
	}

	@Override
	public void deleteById(int id) throws SQLException {
		new Ban_DAO().deleteById(id);
	}
	public ArrayList<Ban>findByName(String name){
		return new Ban_DAO().findByName(name);
	}
	public Ban getBanById(String id) {
		return new Ban_DAO().getBanById(id);
	}
	
}
