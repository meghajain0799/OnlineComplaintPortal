package com.cg.sprint1.proj.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cg.sprint1.proj.entities.Complaint;
import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.entities.Product;
import com.cg.sprint1.proj.exceptions.InValidComplaintIdException;
import com.cg.sprint1.proj.exceptions.InValidModelNumberException;
import com.cg.sprint1.proj.exceptions.InvalidClientIdException;
import com.cg.sprint1.proj.exceptions.OutOfWarrantyException;
import com.cg.sprint1.proj.exceptions.PermissionDeniedException;
import com.cg.sprint1.proj.services.ComplaintService;

@CrossOrigin(origins ="http://localhost:4200")
@RestController
@RequestMapping( "/complaint")
public class ComplaintController {
	@Autowired
	ComplaintService complaintService;

	@PostMapping(value = "/bookComplaint", consumes = "application/json")
	public ResponseEntity<Complaint> bookComplaint(@RequestBody Complaint complaint)
			throws InvalidClientIdException, InValidModelNumberException, OutOfWarrantyException {
		Complaint myComplaint = null;
		try {
			myComplaint = 
			complaintService.bookComplaint(complaint.getProductModelNumber(), complaint.getComplaintName(),
					complaint.getClientId(), complaint.getComplaintRegDate());
		} catch (InvalidClientIdException ex) {
			throw new InvalidClientIdException(" !!! Invalid Client ID Provided !!! ");
		} catch (InValidModelNumberException ex) {
			throw new InValidModelNumberException(" !!! Invalid Model Number Provided !!!");
		} catch (OutOfWarrantyException ex) {
			throw new OutOfWarrantyException("!!! Warranty Date Expired !!!");
		}
		return new ResponseEntity<Complaint>(myComplaint, HttpStatus.OK);
	}

	@PutMapping(value = "/changeStatus/{complaintId}", consumes = "text/plain")
	public ResponseEntity<Complaint> changeComplaintStatus(@PathVariable int complaintId, @RequestBody String status)
			throws PermissionDeniedException {
		Complaint myComplaint = null;
		try {
	
			myComplaint = complaintService.changeComplaintStatus(complaintId,status);
			System.out.println("hfuwdhfuihdfjh");
		} catch (PermissionDeniedException e) {
			throw new PermissionDeniedException(" !!! PERMISSION DENIED !!!");
		}
		return new ResponseEntity<Complaint>(myComplaint, HttpStatus.OK);
	}

	@GetMapping(value = "/client/{clientId}", produces = "application/json")
	public ResponseEntity<List<Complaint>> getClientAllComplaints(@PathVariable("clientId") String clientId) {
		return new ResponseEntity<List<Complaint>>(complaintService.getClientAllComplaints(clientId), HttpStatus.OK);
	}

	@GetMapping(value = "/open/client/{clientId}", produces = "application/json")
	public ResponseEntity<List<Complaint>> getClientAllOpenComplaints(@PathVariable("clientId") String clientId) {
		return new ResponseEntity<List<Complaint>>(complaintService.getClientAllOpenComplaints(clientId),
				HttpStatus.OK);
	}
	
	@GetMapping(value = "/active/client/{clientId}", produces = "application/json")
	public ResponseEntity<List<Complaint>> getClientActiveComplaints(@PathVariable("clientId") String clientId) {
		return new ResponseEntity<List<Complaint>>(complaintService.getClientActiveComplaints(clientId),
				HttpStatus.OK);
	}

	@GetMapping(value = "/engineer/complaintId/{complaintId}", produces = "application/json")
	public ResponseEntity<Engineer> getEngineerByComplaintId(@PathVariable("complaintId") int complaintId)
			throws InValidComplaintIdException {
		Optional<Complaint> c = null;
		c = complaintService.getComplaintByComplaintId(complaintId);
		if (c.isPresent()) {
			return new ResponseEntity<Engineer>(complaintService.getEngineerByComplaintId(complaintId), HttpStatus.OK);
		} else {
			throw new InValidComplaintIdException("!!! INVALID COMPLAINT ID PROVIDED !!!");
		}
	}

	@GetMapping(value = "/product/complaintId/{complaintId}", produces = "application/json")
	public ResponseEntity<Product> getProductByComplaint(@PathVariable("complaintId") int complaintId)
			throws InValidComplaintIdException {
		Optional<Complaint> c = null;
		c = complaintService.getComplaintByComplaintId(complaintId);
		if (c.isPresent()) {
			return new ResponseEntity<Product>(complaintService.getProductByComplaintId(complaintId), HttpStatus.OK);
		} else {
			throw new InValidComplaintIdException("!!! INVALID COMPLAINT ID PROVIDED !!!");
		}

	}
	
	@GetMapping(value = "/complaintId/{complaintId}",produces = "application/json")
	public ResponseEntity<Optional<Complaint>> getComplaintByComplaintId(@PathVariable("complaintId") int complaintId){
		return new ResponseEntity<Optional<Complaint>>(complaintService.getComplaintByComplaintId(complaintId),HttpStatus.OK);
	}
	
	

}