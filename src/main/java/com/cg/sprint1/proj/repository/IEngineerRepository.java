package com.cg.sprint1.proj.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.sprint1.proj.entities.*;
import com.cg.sprint1.proj.exceptions.PermissionDeniedException;


public interface IEngineerRepository extends JpaRepository<Engineer, Integer>{

	@Query("SELECT c FROM Complaint c WHERE c.engineerId = ?1 AND c.status = 'OPEN'")
	public List<Complaint> getAllOpenComplaints(int engineerId );
	
	@Query("SELECT c FROM Complaint c WHERE c.engineerId = ?1 AND c.status IN ('RESOLVED','CLOSED')")
	public List<Complaint> getResolvedComplaints(int engineerId);
	
	
	@Query("SELECT c FROM Complaint c INNER JOIN Engineer e ON (c.engineerId = e.engineerId) WHERE c.status = 'RESOLVED' AND c.engineerId =:engineerId AND c.complaintRegDate<= :date ORDER BY c.complaintRegDate DESC")
	public List<Complaint> getResolvedComplaintsByDate(@Param("engineerId")int engineerId, @Param("date")LocalDate date);
	
	@Query("SELECT c FROM Complaint c WHERE c.engineerId = ?1 AND c.status = status")
	public List<Complaint> getComplaints(int engineerId,String status);
	
	@Modifying
	@Query("UPDATE Complaint c SET c.status =:status WHERE c.complaintId = :complaintId")
	public int changeComplaintStatus(int complaintId, String status)  throws PermissionDeniedException;
	
	@Query("SELECT c FROM Complaint c INNER JOIN Engineer e ON (c.engineerId = e.engineerId) WHERE c.status = 'OPEN' AND c.engineerId =:engineerId ORDER BY c.maximumDateToResolve ")
	public List<Complaint> getOpenComplaintsOrderByPriority(@Param("engineerId")int engineerId);
	
	@Query("SELECT c FROM Complaint c WHERE c.complaintId = ?1")
	public Optional<Complaint> getComplaintByComplaintId(@Param(value = "complaintId") int complaintId);
}
