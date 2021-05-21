package com.cg.sprint1.proj.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Complaint")
public class Complaint {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complaint_Sequence")
	@SequenceGenerator(name = "complaint_Sequence", sequenceName = "complaint_seq", allocationSize = 1, initialValue = 1)
	@Column(name = "complaint_id")
	private int complaintId;
	@Column(name = "product_model_number")
	private String productModelNumber;
	@Column(name = "complaint_name")
	private String complaintName;
	@Column(name = "complaint_status")
	private String status;
	@Column(name = "client_id")
	private String clientId;
	@Column(name = "engineer_id")
	private int engineerId;
	@Column(name = "complaint_regsitration_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate complaintRegDate;
	@Column(name = "max_date_to_resolve")
	private LocalDate maximumDateToResolve;

	public Complaint() {
		super();
	}

	public Complaint(int complaintId, String productModelNumber, String complaintName, String status, String clientId,
			int engineerId, LocalDate complaintRegDate, LocalDate maximumDateToResolve) {
		super();
		this.complaintId = complaintId;
		this.productModelNumber = productModelNumber;
		this.complaintName = complaintName;
		this.status = status;
		this.clientId = clientId;
		this.engineerId = engineerId;
		this.complaintRegDate = complaintRegDate;
		this.maximumDateToResolve = maximumDateToResolve;
	}

	public long getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}

	public String getProductModelNumber() {
		return productModelNumber;
	}

	public void setProductModelNumber(String productModelNumber) {
		this.productModelNumber = productModelNumber;
	}

	public String getComplaintName() {
		return complaintName;
	}

	public void setComplaintName(String complaintName) {
		this.complaintName = complaintName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getEngineerId() {
		return engineerId;
	}

	public void setEngineerId(int engineerId) {
		this.engineerId = engineerId;
	}

	public LocalDate getComplaintRegDate() {
		return complaintRegDate;
	}

	public void setComplaintRegDate(LocalDate complaintRegDate) {
		this.complaintRegDate = complaintRegDate;
	}

	public LocalDate getMaximumDateToResolve() {
		return maximumDateToResolve;
	}

	public void setMaximumDateToResolve(LocalDate maximumDateToResolve) {
		this.maximumDateToResolve = maximumDateToResolve;
	}

}
