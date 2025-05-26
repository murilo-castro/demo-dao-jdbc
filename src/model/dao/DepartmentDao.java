package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	void insert(Department department);
	void update(Department department);
	void deleteById(Integer departmentId);
	Department findDepartmentById(Integer departmentId);
	List<Department> findAllDepartments();
}
