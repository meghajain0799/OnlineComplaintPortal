package com.cg.sprint1.proj.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.sprint1.proj.entities.*;
import com.cg.sprint1.proj.exceptions.*;


public interface IAdminRepository extends JpaRepository<Admin, Integer> {
	@Modifying
	@Query(value = "INSERT INTO Engineer (engineer_Id,engineer_password,engineer_name,engineer_domain) VALUES(:engineerId,:password,:engineerName,:domain)", nativeQuery = true)
	public int addEngineer(@Param("engineerId") int engineerId,@Param("password") String password,@Param("engineerName") String engineerName,@Param("domain") String domain); 
	
	@Modifying
	@Query("UPDATE Engineer e SET e.domain = :newDomain WHERE e.engineerId = :engineerId")
	public int changeDomain(@Param("engineerId")int engineerId,@Param("newDomain")String newDomain)throws InValidDomainException,InvalidEngineerIdException;
	
	@Modifying
	@Query(value = "DELETE FROM Engineer WHERE engineer_id = ?1", nativeQuery = true) 
	public int removeEngineer(@Param("engineerId")int engineerId)throws InvalidEngineerIdException;
	
	@Query("SELECT c FROM Complaint c INNER JOIN Product p ON( c.productModelNumber= p.modelNumber ) WHERE p.productCategoryName = :productCategoryName")
	public List<Complaint> getComplaintsByProducts(@Param("productCategoryName")String productCategoryName);
	
	@Query(value=" SELECT c From Complaint c INNER JOIN Product p ON (c.productModelNumber = p.modelNumber) WHERE c.status =:status AND p.productCategoryName = :productCategoryName")
	public List<Complaint> getComplaints(@Param("status")String status,@Param("productCategoryName")String productCategoryName); 
	
	@Modifying
	@Query("UPDATE Complaint c SET c.engineerId = :engineerId WHERE c.complaintId = :complaintId")
	public int replaceEngineerFromComplaint(@Param("engineerId") int engineerId,@Param("complaintId")int complaintId)throws InValidDomainException;
	
	
	@Query("SELECT e from Engineer e WHERE e.engineerId = :engineerId")
	public Optional<Engineer> getEngineerbyId(@Param("engineerId")int engineerId);
	
	@Query("SELECT e from Engineer e WHERE e.domain =:domain")
	public List<Engineer> getEngineersByDomain(@Param("domain")String domain);
	
	@Query("SELECT c from Complaint c WHERE  c.complaintId = ?1")
	public Optional<Complaint> getComplaintByComplaintId(int complaintId);
	
	@Query("SELECT e from Engineer e WHERE e.engineerId = ?1")
	public Optional<Engineer> getEngineerDomainById(int engineerId);

}
