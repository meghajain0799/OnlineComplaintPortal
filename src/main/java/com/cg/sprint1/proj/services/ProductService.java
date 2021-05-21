package com.cg.sprint1.proj.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.sprint1.proj.entities.Complaint;
import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.entities.Product;
import com.cg.sprint1.proj.exceptions.InValidModelNumberException;
import com.cg.sprint1.proj.repository.IProductRepository;

@Service
public class ProductService implements IProductService {

	@Autowired
	IProductRepository productDao;

	@Transactional
	@Override
	public boolean addProduct(String modelNumber, String productName, String productCategoryName,
			LocalDate dateofPurchase, int warrantyYears, String clientId) {
		boolean methodCheck = false;
		LocalDate warrantyDate = generateWarrantyDate(dateofPurchase, warrantyYears);
		int i = productDao.addProduct(modelNumber, productName, productCategoryName, dateofPurchase, warrantyYears,
				warrantyDate, clientId);
		if (i == 1)
			methodCheck = true;
		return methodCheck;
	}

	@Transactional
	@Override
	public boolean deleteByProductCategoryName(String category) {
		boolean methodCheck = false;
		int i = productDao.deleteByProductCategoryName(category);
		if (i == 1)
			methodCheck = true;
		return methodCheck;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Product> getByProductCategoryName(String categoryName) {
		return productDao.getByProductCategoryName(categoryName);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> getProductsByClientId(String clientId){
		return productDao.getProductsByClientId(clientId);
	}

	@Transactional
	@Override
	public boolean updateProductWarranty(String modelNumber, int warrantyYears) throws InValidModelNumberException {
		boolean methodCheck = false;
		Optional<Product> p = productDao.findByModelNumber(modelNumber);
		if (p.isPresent()) {
			LocalDate dateOfPurchase = productDao.findByModelNumber(modelNumber).get().getDateofPurchase() ;
			LocalDate warrantyDate = generateWarrantyDate(dateOfPurchase, warrantyYears);
			productDao.updateProductWarranty(modelNumber, warrantyYears,warrantyDate);
			methodCheck = true;
		} else {
			throw new InValidModelNumberException("!!! Invalid Model Number Given!!!");
		}
		return methodCheck;

	}

	@Transactional(readOnly = true)
	@Override
	public List<Complaint> getProductComplaints(String productCategoryName) {

		return productDao.getProductComplaints(productCategoryName);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Engineer> getEngineersByProduct(String productCategoryName) {
		return productDao.getEngineersByProduct(productCategoryName);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Product> findByModelNumber(String modelNumber) {
		return productDao.findByModelNumber(modelNumber);
	}

	@Transactional(readOnly = true)
	@Override
	public LocalDate generateWarrantyDate(LocalDate dateOfPurchase, int warrantyYears) {
		LocalDate myWarrantyDate;
		myWarrantyDate = dateOfPurchase.plusYears(warrantyYears);
		return myWarrantyDate;

	}

}
