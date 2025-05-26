package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		SellerDao sellerDao = DaoFactory.creatSellerDao();
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("==== Test 1: Seller findSellerById ====");		
		Seller seller = sellerDao.findSellerById(2);		
		System.out.println(seller);
		
		System.out.println("\n ==== Test 2: Seller findSellerByDepartment ====");
		Department department = new Department(1, null);
		List<Seller> sellersByDepartment = sellerDao.findSellersByDepartment(department);		
		sellersByDepartment.forEach(System.out::println);
		
		System.out.println("\n ==== Test 3: Seller findAllSellers ====");
		List<Seller> sellers = sellerDao.findAllSellers();		
		sellers.forEach(System.out::println);
		
		System.out.println("\n ==== Test 4: Seller insert ====");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.00, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New seller: " + newSeller + ", new Id: " + newSeller.getId());
		
		System.out.println("\n ==== Test 5: Seller update ====");
		seller = sellerDao.findSellerById(8);
		seller.setEmail("sellergreg@gmail.com");
		sellerDao.update(seller);
		System.out.println("Update Completed! " + seller);
		
		System.out.println("\n ==== Test 6: Seller deleteSellerById ====");
		sellerDao.deleteById(13);
		System.out.println("Delete completed");
		
		System.out.println("\n ==== Test 7: Department insert ====");
		Department newDepartment = new Department(null, "Tools");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New department: " + newDepartment + ", new Id: " + newDepartment.getId());
		
		scanner.close();
	}
}
