package com.cg.sprint1.proj.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cg.sprint1.proj.entities.Complaint;
import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.exceptions.InValidComplaintIdException;
import com.cg.sprint1.proj.exceptions.InValidDomainException;
import com.cg.sprint1.proj.exceptions.InvalidEngineerIdException;
import com.cg.sprint1.proj.repository.*;

@Service
public class AdminService implements IAdminService {
	@Autowired
	IAdminRepository adminDao;

	public void setAdminDao(IAdminRepository adminDao) {
		this.adminDao = adminDao;
	}

	@Override
	@Transactional
	public boolean addEngineer(int engineerId, String password, String engineerName, String domain) {
		boolean status = false;
		int i = adminDao.addEngineer(engineerId, password, engineerName, domain);
		if (i == 1)
			status = true;
		return status;
	}

	@Override
	@Transactional
	public boolean changeDomain(int engineerId, String newDomain)
			throws InValidDomainException, InvalidEngineerIdException {
		boolean status = false;
		Optional<Engineer> engineer = adminDao.getEngineerbyId(engineerId);
		if (engineer.isPresent()) {
			List<String> domain = new ArrayList<String>();
			domain.add("AC");
			domain.add("TV");
			domain.add("MOBILE");
			domain.add("WASHING MACHINE");
			if (domain.contains(newDomain)) {
				int i = adminDao.changeDomain(engineerId, newDomain);
				if (i == 1)
					status = true;
			} else {
				throw new InValidDomainException(" !!! Invalid Domain!!!");
			}
		} else {
			throw new InvalidEngineerIdException(" !!! Invalid Engineer ID !!!");
		}

		return status;
	}

	@Override
	@Transactional
	public boolean removeEngineer(int engineerId) throws InvalidEngineerIdException {
		boolean status = false;
		Optional<Engineer> engineer = adminDao.getEngineerbyId(engineerId);
		if (engineer.isPresent()) {
			int i = adminDao.removeEngineer(engineerId);
			if (i == 1)
				status = true;
		} else {
			throw new InvalidEngineerIdException("!!! Invalid Engineer ID Provided !!!");
		}

		return status;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Complaint> getComplaintsByProducts(String productCategoryName) {
		return adminDao.getComplaintsByProducts(productCategoryName);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Complaint> getComplaints(String status, String productCategoryName) {
		return adminDao.getComplaints(status, productCategoryName);
	}

	@Override
	@Transactional
	public boolean replaceEngineerFromComplaint(int complaintId)
			throws InValidDomainException, InValidComplaintIdException {
		boolean methodCheck = false;
		Optional<Complaint> complaint = adminDao.getComplaintByComplaintId(complaintId);
		if (complaint.isPresent()) {
			int givenEngineerId = complaint.get().getEngineerId();
			Optional<Engineer> engineer = adminDao.getEngineerDomainById(givenEngineerId);
			List<Engineer> engineerList = adminDao.getEngineersByDomain(engineer.get().getDomain());
			int engineerId = genEngineerId(engineerList, givenEngineerId);
			int i = adminDao.replaceEngineerFromComplaint(engineerId, complaintId);
			if (i == 1)
				methodCheck = true;
		} else {
			throw new InValidComplaintIdException(" !!! Invalid Complaint ID Provided !!!");
		}
		return methodCheck;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Engineer> getEngineerbyId(int engineerId) {
		return adminDao.getEngineerbyId(engineerId);
	}

	public int genEngineerId(List<Engineer> engineerList, int engineerId) {
		Random random = new Random();
		int randomNumber;
		do {
			randomNumber = random.nextInt(engineerList.size());
			if (engineerList.size() <= 1) {
				System.out.println(" !!! Replacement of Engineer is not Possible !!! ");
				break;
			}
		} while (engineerList.get(randomNumber).getEngineerId() == engineerId);

		System.out.println(randomNumber);
		return engineerList.get(randomNumber).getEngineerId();
	}

}
