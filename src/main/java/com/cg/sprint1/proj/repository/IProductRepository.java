package com.cg.sprint1.proj.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.sprint1.proj.entities.*;
import com.cg.sprint1.proj.exceptions.*;

public interface IProductRepository extends JpaRepository<Product, Integer> {
	@Modifying 
	@Query(value = "INSERT INTO Product(product_model_number,product_name,product_category_name,date_of_purchase,product_warranty_years,product_warranty_date,client_id) values(?1,?2,?3,?4,?5,?6,?7)",nativeQuery = true)
	public int addProduct(String modelNumber,String productName,String productCategoryName,LocalDate dateofPurchase,int warrantyYears,LocalDate warrantyDate,String client_id);
	
	public int deleteByProductCategoryName(String category); 
	
	public List<Product> getByProductCategoryName(String categoryName); 
	
	@Modifying
	@Query("UPDATE Product p SET p.warrantyYears = ?2,p.warrantyDate = ?3 WHERE p.modelNumber = ?1") 
	public int updateProductWarranty(String modelNumber,int warrantyYears,LocalDate warrantyDate)throws InValidModelNumberException;
	
	@Query("SELECT c From Complaint c INNER JOIN Product p ON (c.productModelNumber = p.modelNumber) WHERE p.productCategoryName = :productCategoryName")
	public List<Complaint> getProductComplaints(@Param("productCategoryName") String productCategoryName);
	
	@Query("SELECT e from Engineer e WHERE e.domain =:productCategoryName")
	public List<Engineer> getEngineersByProduct(@Param("productCategoryName")String productCategoryName);
	
	@Query("SELECT p from Product p WHERE p.clientId = :clientId")
	public List<Product> getProductsByClientId(@Param("clientId") String clientId);
	
	public Optional<Product> findByModelNumber(String modelNumber);
	
	@Modifying
	@Query(value = "INSERT INTO Product(product_warranty_date) values(:warrantyDate) WHERE p.modelNumber = :modelNumber",nativeQuery = true)
	public int saveWarrantyDate(@Param("warrantyDate") LocalDate warrantyDate, @Param("modelNumber") String modelNumber); 
	
	
}
