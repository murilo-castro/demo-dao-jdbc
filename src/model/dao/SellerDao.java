package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
	void insert(Seller Seller);
	void update(Seller Seller);
	void deleteById(Integer SellerId);
	Seller findSellerById(Integer SellerId);
	List<Seller> findSellersAll();
}
