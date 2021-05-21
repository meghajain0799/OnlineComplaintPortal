package com.cg.sprint1.proj.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Client")
public class Client {
	@Id
	@Column(name = "client_id")
	private String clientId;
	@Column(name = "client_password")
	private String password;
	@Column(name = "client_address")
	private String address;
	@Column(name = "client_phone_number")
	private long phoneNumber;

	@OneToMany
	@JoinColumn(name = "client_id")
	private List<Product> productOwned;

	@OneToMany
	@JoinColumn(name = "client_id")
	private List<Complaint> complaintList;

	public Client() {
		super();
	}

	public Client(String clientId, String password, String address, long phoneNumber) {
		super();
		this.clientId = clientId;
		this.password = password;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Product> getProductOwned() {
		return productOwned;
	}

	public void setProductOwned(List<Product> productOwned) {
		this.productOwned = productOwned;
	}

	public List<Complaint> getComplaintList() {
		return complaintList;
	}

	public void setComplaintList(List<Complaint> complaintList) {
		this.complaintList = complaintList;
	}

}
