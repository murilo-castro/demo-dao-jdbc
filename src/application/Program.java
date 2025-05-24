package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {
		SellerDao sellerDao = DaoFactory.creatSellerDao();
		
		System.out.println("==== Test 1:Seller findSellerById ====");		
		Seller seller = sellerDao.findSellerById(2);		
		System.out.println(seller);
		
		System.out.println("\n ==== Test 2:Seller findSellerByDepartment ====");
		Department department = new Department(1, null);
		List<Seller> sellers = sellerDao.findSellersByDepartment(department);		
		sellers.forEach(System.out::println);
	}
}
