package com.cg.sprint1.proj.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.sprint1.proj.entities.Complaint;
import com.cg.sprint1.proj.exceptions.InValidComplaintIdException;
import com.cg.sprint1.proj.exceptions.PermissionDeniedException;
import com.cg.sprint1.proj.repository.IEngineerRepository;
import com.cg.sprint1.proj.status.advice.Status;

@Service
public class EngineerService implements IEngineerService {
	@Autowired
	IEngineerRepository engineerDao;

	@Transactional(readOnly = true)
	@Override
	public List<Complaint> getAllOpenComplaints(int engineerId) {
		return engineerDao.getAllOpenComplaints(engineerId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Complaint> getResolvedComplaints(int engineerId) {
		return engineerDao.getResolvedComplaints(engineerId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Complaint> getResolvedComplaintsByDate(int engineerId, LocalDate date) {

		return engineerDao.getResolvedComplaintsByDate(engineerId, date);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Complaint> getComplaints(int engineerId, String status) {
		return engineerDao.getComplaints(engineerId, status);
	}

	@Transactional
	@Override
	public boolean changeComplaintStatus(int complaintId, String status)
			throws PermissionDeniedException, InValidComplaintIdException {
		boolean methodCheck = false;
		Optional<Complaint> complaint = engineerDao.getComplaintByComplaintId(complaintId);
		if (complaint.isPresent()) {
			if (status.equals(Status.CLOSED.toString())) {
				throw new PermissionDeniedException(" !!! PERMISSION DENIED !!! ");
			} else {
				int i = engineerDao.changeComplaintStatus(complaintId, status);
				if (i == 1)
					methodCheck = true;
			}
		} else {
			throw new InValidComplaintIdException("!!! INVALID COMPLAINT ID PROVIDED !!!");
		}

		return methodCheck;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Complaint> getOpenComplaintsOrderByPriority(int engineerId) {
		return engineerDao.getOpenComplaintsOrderByPriority(engineerId);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Complaint> getComplaintByComplaintId(int complaintId) {
		return engineerDao.getComplaintByComplaintId(complaintId);
	}

}
