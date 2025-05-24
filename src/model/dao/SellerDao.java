package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	void insert(Seller seller);
	void update(Seller seller);
	void deleteById(Integer sellerId);
	Seller findSellerById(Integer SellerId);
	List<Seller> findAllSellers();
	List<Seller> findSellersByDepartment(Department department);
}
