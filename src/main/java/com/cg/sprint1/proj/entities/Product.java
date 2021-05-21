package com.cg.sprint1.proj.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {
	@Id
	@Column(name = "product_model_number")
	private String modelNumber;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "product_category_name")
	private String productCategoryName;
	@Column(name = "date_of_purchase")
	private LocalDate dateofPurchase;
	@Column(name = "product_warranty_years")
	private int warrantyYears;
	@Column(name = "product_warranty_date")
	private LocalDate warrantyDate;
	@Column(name = "client_id")
	private String clientId;

	public Product() {
		super();
	}

	public Product(String modelNumber, String productName, String productCategoryName, LocalDate dateofPurchase,
			int warrantyYears, LocalDate warrantyDate, String clientId) {
		super();
		this.modelNumber = modelNumber;
		this.productName = productName;
		this.productCategoryName = productCategoryName;
		this.dateofPurchase = dateofPurchase;
		this.warrantyYears = warrantyYears;
		this.warrantyDate = warrantyDate;
		this.clientId = clientId;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public LocalDate getDateofPurchase() {
		return dateofPurchase;
	}

	public void setDateofPurchase(LocalDate dateofPurchase) {
		this.dateofPurchase = dateofPurchase;
	}

	public int getWarrantyYears() {
		return warrantyYears;
	}

	public void setWarrantyYears(int warrantyYears) {
		this.warrantyYears = warrantyYears;
	}

	public LocalDate getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(LocalDate warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String client_id) {
		this.clientId = client_id;
	}

}
