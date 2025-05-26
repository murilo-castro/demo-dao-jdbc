package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement statment = null;

		try {
			statment = conn.prepareStatement("INSERT INTO department " + "(Name) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			statment.setString(1, department.getName());

			int rowsAffected = statment.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet resultSet = statment.getGeneratedKeys();

				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					department.setId(id);
				}

				DB.closeResultSet(resultSet);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
		}
	}

	@Override
	public void update(Department department) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer departmentId) {

	}

	@Override
	public Department findDepartmentById(Integer departmentId) {
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		
		try {
			statment = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
			
			statment.setInt(1, departmentId);
			resultSet = statment.executeQuery();
			
			if (resultSet.next()) {
				return instantiateDepartment(resultSet); 
			}
			
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
			DB.closeResultSet(resultSet);
		}
	}

	@Override
	public List<Department> findAllDepartments() {
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		
		try {
			statment = conn.prepareStatement("SELECT * FROM department");
			resultSet = statment.executeQuery();
			List<Department> departments = new ArrayList<Department>();
			while (resultSet.next()) {
				departments.add(instantiateDepartment(resultSet));
			}
			
			return departments;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
			DB.closeResultSet(resultSet);
		}
	}
	
	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		return new Department(resultSet.getInt("Id"), resultSet.getString("Name"));
	}

}
