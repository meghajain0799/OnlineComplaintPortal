package com.cg.sprint1.proj.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Engineer")
public class Engineer {
	@Id
	@Column(name = "engineer_id")
	private int engineerId;
	@Column(name = "engineer_password")
	private String password;
	@Column(name = "engineer_name")
	private String engineerName;
	@Column(name = "engineer_domain")
	private String domain;
	@OneToMany
	@JoinColumn(name = "engineer_id")
	private List<Complaint> complaints;

	public Engineer() {
		super();
	}

	public Engineer(int employeeId, String password, String engineerName, String domain) {
		super();
		this.engineerId = employeeId;
		this.password = password;
		this.engineerName = engineerName;
		this.domain = domain;
	}

	public int getEngineerId() {
		return engineerId;
	}

	public void setEngineerId(int employeeId) {
		this.engineerId = employeeId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEngineerName() {
		return engineerName;
	}

	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public List<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(List<Complaint> complaints) {
		this.complaints = complaints;
	}

	@Override
	public String toString() {
		return "Engineer [engineerId=" + engineerId + ", password=" + password + ", engineerName=" + engineerName
				+ ", domain=" + domain + "]";
	}

}
