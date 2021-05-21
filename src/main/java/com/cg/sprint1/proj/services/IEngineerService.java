package com.cg.sprint1.proj.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cg.sprint1.proj.entities.*;
import com.cg.sprint1.proj.exceptions.InValidComplaintIdException;
import com.cg.sprint1.proj.exceptions.PermissionDeniedException;

public interface IEngineerService {

	public List<Complaint> getAllOpenComplaints(int engineerId);

	public List<Complaint> getResolvedComplaints(int engineerId);

	public List<Complaint> getResolvedComplaintsByDate(int engineerId, LocalDate date);

	public List<Complaint> getComplaints(int engineerId, String status);

	public boolean changeComplaintStatus(int complaintId, String status)
			throws PermissionDeniedException, InValidComplaintIdException;

	public List<Complaint> getOpenComplaintsOrderByPriority(int engineerId);

	public Optional<Complaint> getComplaintByComplaintId(int complaintId);

}
