package com.cg.sprint1.proj.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.sprint1.proj.entities.Complaint;
import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.entities.Product;
import com.cg.sprint1.proj.exceptions.InValidModelNumberException;
import com.cg.sprint1.proj.services.ProductService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productservice;
	
	@PostMapping(value = "/add",consumes = "application/json")
	public ResponseEntity<String> addProduct(@RequestBody Product product) {
		productservice.addProduct(product.getModelNumber(),product.getProductName(),product.getProductCategoryName(),product.getDateofPurchase(),product.getWarrantyYears(),product.getClientId());
		return new ResponseEntity<String>(" *** PRODUCT ADDED SUCCESSFULLY *** ",HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{category}")
	public ResponseEntity<String> deleteByProductCategoryName(@PathVariable("category") String category) {
		productservice.deleteByProductCategoryName(category);
		return new ResponseEntity<String>(" *** PRODUCTS DELETED SUCCESSFULLY***",HttpStatus.OK);
		
	}
	
	@PutMapping(value = "/updateWarranty",consumes = "application/json")
	public ResponseEntity<String> updateProductWarranty(@RequestParam String modelNumber,@RequestParam int warrantyYears) throws InValidModelNumberException {
		Optional<Product> p = null;
		p = productservice.findByModelNumber(modelNumber);
		if(p.isPresent()) {
			productservice.updateProductWarranty(modelNumber,warrantyYears);
		}
		else {
			throw new InValidModelNumberException("!!! Given ModelNumber is Invalid !!!");
		}
		return new ResponseEntity<String>("*** WARRANTY UPDATED SUUCESSFULLY ***",HttpStatus.OK);
	}
	
	@GetMapping(value = "/categoryName/{productCategoryName}",produces = "application/json")
	public ResponseEntity<List<Product>>  getByProductCategoryName(@PathVariable("productCategoryName") String productCategoryName){
		return new ResponseEntity<List<Product>>(productservice.getByProductCategoryName(productCategoryName),HttpStatus.OK);
	}
	@GetMapping(value = "/productCategoryName/complaint/{productCategoryName}", produces = "application/json")
	public ResponseEntity<List<Complaint>> getProductComplaints(@PathVariable("productCategoryName") String productCategoryName){
		return new ResponseEntity<List<Complaint>>(productservice.getProductComplaints(productCategoryName),HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/clientId/{clientId}", produces = "application/json")
	public ResponseEntity<List<Product>> getProductsByClientId(@PathVariable("clientId") String clientId){
		return new ResponseEntity<List<Product>>(productservice. getProductsByClientId(clientId),HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/productCategoryName/engineer/{productCategoryName}", produces = "application/json")
	public ResponseEntity<List<Engineer>> getEngineersByProduct(@PathVariable("productCategoryName") String productCategoryName){
		return new ResponseEntity<List<Engineer>> (productservice.getEngineersByProduct(productCategoryName),HttpStatus.OK);
		
	}
}

