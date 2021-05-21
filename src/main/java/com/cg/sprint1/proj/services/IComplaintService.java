package com.cg.sprint1.proj.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.cg.sprint1.proj.entities.*;
import com.cg.sprint1.proj.exceptions.*;

public interface IComplaintService {

	public Complaint bookComplaint(String product_model_number, String complaint_name, String client_id,
			LocalDate complaint_regsitration_date)
			throws OutOfWarrantyException, InvalidClientIdException, InValidModelNumberException;

	
	  public Complaint changeComplaintStatus(int complaintId, String status) throws
	  PermissionDeniedException;
	 
   //public Complaint changeComplaintStatus(Complaint complaint) throws PermissionDeniedException;

	public List<Complaint> getClientAllComplaints(String clientId);

	public List<Complaint> getClientAllOpenComplaints(String clientId);
	
	public List<Complaint> getClientActiveComplaints(String clientId);

	public Engineer getEngineerByComplaintId(int complaintId) throws InValidComplaintIdException;

	public Product getProductByComplaintId(int complaintId) throws InValidComplaintIdException;

	public Optional<Complaint> getComplaintByComplaintId(int complaintId);

	public Optional<Client> checkValidClientId(String clientId);

	public Optional<Product> checkValidModelNumber(String modelNumber);

	public LocalDate genMaxDateToResolve(String modelNumber, LocalDate regDate);

	public List<Engineer> getEngineersByDomain(String productCategoryName);

	public int genEngineerId(List<Engineer> engineerList);

	public int genComplaintId();
	

}
