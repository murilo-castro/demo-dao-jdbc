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

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;	

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller Seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller Seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer SellerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findSellerById(Integer sellerId) {
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		
		try {
			statment = conn.prepareStatement(
					"SELECT s.*, d.Name as DepartmentName "
					+ "FROM seller s INNER JOIN department d "
					+ "ON s.DepartmentId = d.Id "
					+ "WHERE s.Id = ?");
			
			statment.setInt(1, sellerId);
			resultSet = statment.executeQuery();
			
			if (resultSet.next()) {
				Department department = instantiateDepartment(resultSet);				
				Seller seller = instatiateSeller(resultSet, department);
				
				return seller;
			}
			
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
			DB.closeResultSet(resultSet);
		}		
	}

	private Seller instatiateSeller(ResultSet resultSet, Department department) throws SQLException {
		Seller seller = new Seller();
		
		seller.setId(resultSet.getInt("Id"));
		seller.setName(resultSet.getString("Name"));
		seller.setEmail(resultSet.getString("Email"));
		seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
		seller.setBirthDate(resultSet.getDate("BirthDate"));
		seller.setDepartmentId(department);
		
		return seller;
	}

	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		Department department = new Department();
		
		department.setId(resultSet.getInt("DepartmentId"));
		department.setName(resultSet.getString("DepartmentName"));
		
		return department;
	}

	@Override
	public List<Seller> findSellersAll() {
		return null;
	}

	@Override
	public List<Seller> findSellersByDepartment(Department department) {
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		
		try {
			statment = conn.prepareStatement(
					"SELECT s.*, d.Name as DepartmentName "
					+ "FROM seller s INNER JOIN department d "
					+ "ON s.DepartmentId = d.Id "
					+ "WHERE d.Id = ? "
					+ "ORDER BY Name");
			
			statment.setInt(1, department.getId());
			resultSet = statment.executeQuery();
			List<Seller> sellers = new ArrayList<Seller>();
			
			Map<Integer, Department> map = new HashMap<>();
			
			while (resultSet.next()) {
				Department dep = map.get(resultSet.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(resultSet);				
					map.put(resultSet.getInt("DepartmentId"), dep);
				}
								
				Seller seller = instatiateSeller(resultSet, dep);				
				sellers.add(seller);
			}
			
			return sellers;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
			DB.closeResultSet(resultSet);
		}
	}
}
