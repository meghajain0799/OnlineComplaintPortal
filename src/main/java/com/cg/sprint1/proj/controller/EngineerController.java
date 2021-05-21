package com.cg.sprint1.proj.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.sprint1.proj.entities.Complaint;
//import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.exceptions.InValidComplaintIdException;
import com.cg.sprint1.proj.exceptions.PermissionDeniedException;
import com.cg.sprint1.proj.services.EngineerService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/engineer")
public class EngineerController {
	@Autowired
	EngineerService engineerservice;

	@GetMapping(value = "/opencomplaints/{engineerId}", produces = "application/json")
	public ResponseEntity<List<Complaint>> getAllOpenComplaints(@PathVariable("engineerId") int engineerId) {
		return new ResponseEntity<List<Complaint>>(engineerservice.getAllOpenComplaints(engineerId), HttpStatus.OK);
	}

	@GetMapping(value = "/resolvedcomplaints/{engineerId}", produces = "application/json")
	public ResponseEntity<List<Complaint>> getResolvedComplaints(@PathVariable("engineerId") int engineerId) {
		return new ResponseEntity<List<Complaint>>(engineerservice.getResolvedComplaints(engineerId), HttpStatus.OK);
	}

	@GetMapping(value = "/resolvedcomplaints/{engineerId}/date", produces = "application/json")
	public ResponseEntity<List<Complaint>> getResolvedComplaintsByDate(@PathVariable("engineerId") int engineerId,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		return new ResponseEntity<List<Complaint>>(engineerservice.getResolvedComplaintsByDate(engineerId, date),
				HttpStatus.OK);
	}

	@GetMapping(value = "/complaints/{engineerId}/status/{status}", produces = "application/json")
	public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("engineerId") int engineerId,
			@PathVariable("status") String status) {
		return new ResponseEntity<List<Complaint>>(engineerservice.getComplaints(engineerId, status), HttpStatus.OK);
	}

	@PutMapping(value = "/changeStatus", consumes = "application/json")
	public ResponseEntity<String> changeComplaintStatus(@RequestParam int complaintId, @RequestParam String status)
			throws PermissionDeniedException, InValidComplaintIdException {
		try {
			engineerservice.changeComplaintStatus(complaintId, status);
		} catch (PermissionDeniedException e) {
			throw new PermissionDeniedException("!!! PERMISSION DENIED !!!");
		} catch (InValidComplaintIdException e) {
			throw new InValidComplaintIdException(" !!! INVALID COMPLAINT ID PROVIDED  !!!");
		}
		return new ResponseEntity<String>(" *** STATUS CHANGED SUCCESSFULLY *** ", HttpStatus.OK);
	}

	@GetMapping(value = "/opencomplaints/priority/{engineerId}", produces = "application/json")
	public ResponseEntity<List<Complaint>> getOpenComplaintsOrderByPriority(
			@PathVariable("engineerId") int engineerId) {
		return new ResponseEntity<List<Complaint>>(engineerservice.getOpenComplaintsOrderByPriority(engineerId),
				HttpStatus.OK);
	}
}
