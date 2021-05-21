package com.cg.sprint1.proj.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.sprint1.proj.entities.*;
import com.cg.sprint1.proj.exceptions.*;


public interface IClientRepository extends JpaRepository<Client, String> {
		@Modifying
		@Query(value = "INSERT INTO Client(client_id,client_password,client_address,client_phone_number) VALUES (:clientId,:password,:address,:phoneNumber)",nativeQuery = true)
		public int saveClient(String clientId,String password,String address,long phoneNumber); 
		
		@Query("SELECT c from Client c WHERE c.clientId = :clientId") 
		public Optional<Client> getClientByClientId(@Param("clientId") String clientId)throws InvalidClientIdException ; 
		
		@Query("SELECT e from Engineer e WHERE e.engineerId = :engineerId") 
		public Optional<Engineer> getEngineerById(@Param("engineerId")int engineerId) throws InvalidEngineerIdException; 
		
		@Query("SELECT e from Engineer e WHERE e.domain = :category")
		public List<Engineer> getEngineersByDomain(@Param("category")String category); 
		
		@Modifying
		@Query("UPDATE Complaint c SET c.status =:status WHERE c.complaintId = :complaintId ") 
		public int changeStatusOfComplaint(@Param("complaintId")int complaintId,@Param("status")String status)  throws PermissionDeniedException; //working
		
		@Query("SELECT c.password From Client c WHERE c.clientId=?1 ")
		public String signIn(String clientId) throws InvalidCredentialsException, InvalidClientIdException;

		

}
