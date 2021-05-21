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


public interface IComplaintRepository extends JpaRepository<Complaint, Integer>{
	
	@Modifying																																				
	@Query(value = "Insert into Complaint (complaint_id,product_model_number,complaint_name,complaint_status,client_id,engineer_id,complaint_regsitration_date,max_date_to_resolve) VALUES(?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
	public int bookComplaint(int complaintId,String productModelNumber,String complaintName,String complaintStatus, String clientId, int engineerId, LocalDate complaintRegsitration_date,LocalDate maxDateToResolve)throws OutOfWarrantyException,InvalidClientIdException;
	
	@Modifying
	@Query("UPDATE Complaint c SET c.status =:status WHERE c.complaintId = :complaintId") 
	public int changeComplaintStatus(@Param("complaintId") int complaintId, String status) throws PermissionDeniedException;
	
//	@Modifying
//	@Query("UPDATE Complaint c SET c.status =:status WHERE c.complaintId = :complaintId") 
//	public int changeComplaintStatus(Complaint complaint) throws PermissionDeniedException;
//	
	@Query(value=" SELECT c.* From Complaint c INNER JOIN Client cl ON (c.client_id = cl.client_id) WHERE c.client_id = :clientId",nativeQuery=true)
	public List<Complaint> getClientAllComplaints(@Param("clientId") String clientId); 
	
	@Query(value="SELECT c.* From Complaint c INNER JOIN Client cl ON (c.client_id = cl.client_id) WHERE c.complaint_status = 'OPEN' AND c.client_id = :clientId ",nativeQuery=true)
	public List<Complaint> getClientAllOpenComplaints(@Param("clientId")String clientId); 
	
	@Query(value="SELECT c.* From Complaint c INNER JOIN Client cl ON (c.client_id = cl.client_id) WHERE c.complaint_status = 'OPEN' OR c.complaint_status = 'RESOLVE ONLINE' OR c.complaint_status = 'RESOLVE AFTER HOME VISIT' OR c.complaint_status = 'RESOLVED' AND c.client_id = :clientId ",nativeQuery=true)
	public List<Complaint> getClientActiveComplaints(String clientId);
 
	@Query("SELECT e FROM Engineer e INNER JOIN Complaint c ON(e.engineerId = c.engineerId) WHERE c.complaintId = :complaintId")
	public Engineer getEngineerByComplaintId(@Param("complaintId") int complaintId)throws InValidComplaintIdException; 
	
	@Query("SELECT p FROM Product p INNER JOIN Complaint c ON(p.modelNumber = c.productModelNumber) WHERE c.complaintId = :complaintId")
	public Product getProductByComplaintId(@Param("complaintId") int complaintId)throws InValidComplaintIdException; 
	
	@Query(value="SELECT * FROM Complaint ",nativeQuery = true)
	public List<Complaint> getAllComplaints();
	
	@Query("SELECT c FROM Complaint c WHERE c.complaintId = :complaintId")
	public Optional<Complaint> getComplaintByComplaintId(@Param("complaintId")int complaintId);
	
	@Query("SELECT c FROM Client c WHERE c.clientId = :clientId")
	public Optional<Client> checkValidClientId(@Param("clientId") String clientId);
	
	@Query("SELECT p FROM Product p WHERE p.modelNumber = :modelNumber")
	public Optional<Product> checkValidModelNumber(@Param ("modelNumber")String modelNumber);
	
	@Query("SELECT e from Engineer e WHERE e.domain =:productCategoryName")
	public List<Engineer> getEngineersByDomain(@Param("productCategoryName")String productCategoryName);
	

		
}
