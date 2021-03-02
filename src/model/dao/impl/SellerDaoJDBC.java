package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT seller.*,department.Name as DepName " 
				+ "FROM seller INNER JOIN department " 
				+ "ON seller.DepartmentId = department.Id " 
				+ "WHERE seller.Id = ?"
				);
			st.setInt(1, id);
			rs = st.executeQuery(); //executa o comando SQL e fica salvo en rs. rs-> referencia a posicao 0
			if(rs.next()) { //por isso uso rs.next()
				Department dep = instantiateDepartment(rs); 
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			// TODO: handle exception
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}		
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBithDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep); //uso objeto Department por asociacao
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;		
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
				    + "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name"
				    );

			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) { //por isso uso rs.next()
				
				Department dep = map.get(rs.getInt("DepartmentId")); //busco se o departamento existe ou nao na DB
				
				if(dep == null) { //quer dizer que o departamento nao existe ainda
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep); //Salva dep no map para a seguinte iteracao do while
				}
				
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			// TODO: handle exception
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}		
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		// TODO Auto-generated method stub
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
				    + "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
				    + "WHERE DepartmentId = ? "
					+ "ORDER BY Name"
				    );
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) { //por isso uso rs.next()
				
				Department dep = map.get(rs.getInt("DepartmentId")); //busco se o departamento existe ou nao na DB
				
				if(dep == null) { //quer dizer que o departamento nao existe ainda
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep); //Salva dep no map para a seguinte iteracao do while
				}
				
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			// TODO: handle exception
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}			
	}
	
	

}
