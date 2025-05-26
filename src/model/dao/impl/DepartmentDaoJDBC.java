package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		// TODO Auto-generated method stub

	}

	@Override
	public Department findDepartmentById(Integer departmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> findDepartmentsAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
