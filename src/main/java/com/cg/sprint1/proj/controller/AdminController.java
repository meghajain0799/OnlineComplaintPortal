package com.cg.sprint1.proj.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.sprint1.proj.entities.Complaint;
import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.exceptions.InValidComplaintIdException;
import com.cg.sprint1.proj.exceptions.InValidDomainException;
import com.cg.sprint1.proj.exceptions.InvalidEngineerIdException;
import com.cg.sprint1.proj.services.AdminService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminService adminservice;

	public void setAdminService(AdminService adminservice) {
		this.adminservice = adminservice;
	}

	@PostMapping(value= "/addEngineer",consumes = "application/json")
	public ResponseEntity<String> addEngineer(@RequestBody Engineer e) {
		adminservice.addEngineer(e.getEngineerId(),e.getPassword(),e.getEngineerName(),e.getDomain());
		return new ResponseEntity<String>(" *** ENGINEER ADDED SUCCESFULLY *** ",HttpStatus.OK);
	}

	@PutMapping(value = "/changedomain",consumes = "application/json")
	public ResponseEntity<String> changeDomain(@RequestParam int engineerId, @RequestParam String newDomain) throws InValidDomainException, InvalidEngineerIdException {
		List<String> domain = new ArrayList<String>();
		domain.add("AC");
		domain.add("TV");
		domain.add("MOBILE");
		domain.add("WASHING MACHINE");
		Optional<Engineer> e = null;
		e = adminservice.getEngineerbyId(engineerId);
		if((e.isPresent() == true) && domain.contains(newDomain)) {
			adminservice.changeDomain(engineerId, newDomain);
		}
		else if(e.isPresent() == false) {
			throw new InvalidEngineerIdException("!!! Given EngineerId is invalid !!!");
		}
		else {
			throw new InValidDomainException("!!! Given Engineer Domain is invalid !!!");
		}
		return new ResponseEntity<String>(" *** ENGINEER DOMAIN CHANGED SUCCESSFULLY *** ",HttpStatus.OK);
	}

	@DeleteMapping(value = "/{engineerId}")
	public ResponseEntity<String> removeEngineer(@PathVariable int engineerId) throws InvalidEngineerIdException {
		Optional<Engineer> e = null;
		e = adminservice.getEngineerbyId(engineerId);
		if(e.isPresent() == true) {
			adminservice.removeEngineer(engineerId);
		}
		else {
			throw new InvalidEngineerIdException("!!! Given EngineerId is invalid !!!");
		}
		return new ResponseEntity<String>(" *** ENGINEER REMOVED SUCCESSFULLY *** ",HttpStatus.OK);
	}

	@GetMapping(value = "/productCategoryName/{productCategoryName}", produces = "application/json")
	public ResponseEntity<List<Complaint>> getComplaintsByProducts(@PathVariable("productCategoryName") String productCategoryName) {
		return new ResponseEntity<List<Complaint>>(adminservice.getComplaintsByProducts(productCategoryName),
				HttpStatus.OK);
	}

	@GetMapping(value = "/status/{status}/productCategoryName/{productCategoryName}", produces = "application/json")
	public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("status") String status,@PathVariable("productCategoryName") String productCategoryName) {
		return new ResponseEntity<List<Complaint>>(adminservice.getComplaints(status, productCategoryName),
				HttpStatus.OK);
	}
	
	@PutMapping(value = "/replace",consumes = "application/json") // extracting product category
	public ResponseEntity<String> replaceEngineerFromComplaint(@RequestParam int complaintId) throws InValidDomainException, InValidComplaintIdException{
		try {
			adminservice.replaceEngineerFromComplaint(complaintId);
		} catch (InValidDomainException e) {
			throw new InValidDomainException(" !!! Invalid Domain Provided !!! ");
		}
		catch (InValidComplaintIdException e){
			throw new InValidComplaintIdException(" !!! Invalid Complaint ID Provided !!! ");
		}
		return new ResponseEntity<String>(" *** ENGINEER REPLACED SUCCESSFULLY *** ",HttpStatus.OK);
	}

}
