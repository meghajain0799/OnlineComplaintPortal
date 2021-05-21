package com.cg.sprint1.proj.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cg.sprint1.proj.entities.*;
import com.cg.sprint1.proj.exceptions.*;
/* public interface IProductRepository {
	
	public void addProduct(Product product);
	public void removeProducts(String category);
	public List<Product> getProduct(String categoryName);
	
	public void updateProductWarranty(String modelNumber)throws InValidModelNumberException;
	
	public List<Complaint> getProductComplaints(String productCategoryName);
	public List<Engineer> getEngineersByProduct(String productCategoryName);
	
	
	
}*/

public interface IProductService {
	
	public boolean addProduct(String modelNumber,String productName,String productCategoryName,LocalDate dateofPurchase,int warrantyYears,String clientId);
	public boolean deleteByProductCategoryName(String category);
	public List<Product> getByProductCategoryName(String categoryName);
	
	public boolean updateProductWarranty(String modelNumber,int warrantyYears)throws InValidModelNumberException;
	
	public List<Complaint> getProductComplaints(String productCategoryName);
	public List<Engineer> getEngineersByProduct(String productCategoryName);
	
	public Optional<Product> findByModelNumber(String modelNumber);
	
	public LocalDate generateWarrantyDate(LocalDate dateOfPurchase,int warrantyYears);
	public List<Product> getProductsByClientId(String clientId);
	
	
	
}
