package com.cg.sprint1.proj.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cg.sprint1.proj.entities.Client;
import com.cg.sprint1.proj.entities.Complaint;
import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.entities.Product;
import com.cg.sprint1.proj.exceptions.InValidComplaintIdException;
import com.cg.sprint1.proj.exceptions.InValidModelNumberException;
import com.cg.sprint1.proj.exceptions.InvalidClientIdException;
import com.cg.sprint1.proj.exceptions.OutOfWarrantyException;
import com.cg.sprint1.proj.exceptions.PermissionDeniedException;
import com.cg.sprint1.proj.repository.IComplaintRepository;
import com.cg.sprint1.proj.status.advice.Status;

@Service
public class ComplaintService implements IComplaintService {
	@Autowired
	IComplaintRepository complaintDao;


	@Transactional
	@Override
	public Complaint bookComplaint(String productModelNumber, String complaintName, String clientId,
			LocalDate complaintRegDate)
			throws OutOfWarrantyException, InvalidClientIdException, InValidModelNumberException {
		boolean methodCheck = false;
		int complaintId=0;
		Optional<Product> checkModelNumber = complaintDao.checkValidModelNumber(productModelNumber);

		if (checkModelNumber.get().getClientId().equals(clientId)) {

			if (checkModelNumber.isPresent()) {

				Period difference = Period.between(LocalDate.now(), checkModelNumber.get().getWarrantyDate());
				if (difference.getYears() >= 0 && difference.getMonths() >= 0 && difference.getDays() >= 0) {
					LocalDate maximumDateToResolve = genMaxDateToResolve(productModelNumber, complaintRegDate);
					String productCategory = checkModelNumber.get().getProductCategoryName();
					List<Engineer> myEngineer = complaintDao.getEngineersByDomain(productCategory);
					int myEngineerId = genEngineerId(myEngineer);
					 complaintId = genComplaintId();
					System.out.println("complaint done");
					int i = complaintDao.bookComplaint(complaintId, productModelNumber, complaintName, "OPEN", clientId,
							myEngineerId, complaintRegDate, maximumDateToResolve);
					if (i == 1)
						methodCheck = true;
				} else {
					throw new OutOfWarrantyException("!!! Warranty Date Expired !!!");
				}
			} else {
				throw new InValidModelNumberException(" !!! Invalid Model Number !!!");
			}
		} else {
			throw new InvalidClientIdException(" !!! Invalid Client ID Provided !!!");
		}
		return getComplaintByComplaintId(complaintId).get();
	}

	
	/*
	 * @Transactional
	 * 
	 * @Override public Complaint changeComplaintStatus(int complaintId, String
	 * status) throws PermissionDeniedException { boolean methodCheck = false;
	 * 
	 * 
	 * complaintDao.changeComplaintStatus(complaintId, status);
	 * 
	 * Optional<Complaint> mycomplaint = getComplaintByComplaintId(complaintId);
	 * return mycomplaint.get(); }
	 */
	 
	
	@Transactional
	@Override
	public Complaint changeComplaintStatus(int complaintId, String status) throws PermissionDeniedException {
		//boolean methodCheck = false;
		if (status.equals(Status.CLOSED.toString())) {
			//methodCheck = true;
			complaintDao.changeComplaintStatus(complaintId, status);
		} 
		else {
			throw new PermissionDeniedException("!!! PERMISSION DENIED !!!");
		}
		Optional<Complaint> mycomplaint = getComplaintByComplaintId(complaintId);
		  return mycomplaint.get(); 
	}

	@Transactional(readOnly = true)
	@Override
	public List<Complaint> getClientAllComplaints(String clientId) {
		return complaintDao.getClientAllComplaints(clientId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Complaint> getClientAllOpenComplaints(String clientId) {
		return complaintDao.getClientAllOpenComplaints(clientId);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Complaint> getClientActiveComplaints(String clientId){
		return complaintDao.getClientActiveComplaints(clientId);
	}

	@Transactional(readOnly = true)
	@Override
	public Engineer getEngineerByComplaintId(int complaintId) throws InValidComplaintIdException {
		Optional<Complaint> complaint = complaintDao.getComplaintByComplaintId(complaintId);
		if (complaint.isPresent()) {
			return complaintDao.getEngineerByComplaintId(complaintId);
		} else {
			throw new InValidComplaintIdException("!!! Invalid Complaint ID !!!");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Product getProductByComplaintId(int complaintId) throws InValidComplaintIdException {
		Optional<Complaint> complaint = complaintDao.getComplaintByComplaintId(complaintId);
		if (complaint.isPresent()) {
			return complaintDao.getProductByComplaintId(complaintId);
		} else {
			throw new InValidComplaintIdException("!!!  Invalid Complaint ID !!!");
		}
	}

	public Optional<Complaint> getComplaintByComplaintId(int complaintId) {
		return complaintDao.getComplaintByComplaintId(complaintId);
	}

	public Optional<Client> checkValidClientId(String clientId) {
		return complaintDao.checkValidClientId(clientId);
	}

	public Optional<Product> checkValidModelNumber(String modelNumber) {
		return complaintDao.checkValidModelNumber(modelNumber);
	}

	public LocalDate genMaxDateToResolve(String modelNumber, LocalDate regDate) {
		Optional<Product> product = complaintDao.checkValidModelNumber(modelNumber);
		String productCategory;
		if (product.isPresent()) {
			productCategory = product.get().getProductCategoryName();
			switch (productCategory) {
			case "TV":
				return regDate.plusDays(5);
			case "MOBILE":
				return regDate.plusDays(3);
			case "WASHING MACHINE":
				return regDate.plusDays(7);
			case "AC":
				return regDate.plusDays(10);
			default:
				return regDate.plusDays(5);
			}
		}
		return regDate;
	}

	public List<Engineer> getEngineersByDomain(String productCategoryName) {
		return complaintDao.getEngineersByDomain(productCategoryName);
	}

	public int genEngineerId(List<Engineer> engineerList) {
		Random random = new Random();
		int randomNumber;
		randomNumber = random.nextInt(engineerList.size());
		return engineerList.get(randomNumber).getEngineerId();
	}

	public int genComplaintId() {
		Random random = new Random();
		int randomNumber;
		int size = 0;
		List<Complaint> complaint = complaintDao.getAllComplaints();
		do {
			randomNumber = random.nextInt(500);
			size++;
			if (size == complaint.size()) {
				System.out.println("complaint database full");
				break;
			}
		} while (complaintDao.findById(randomNumber).isPresent());

		return randomNumber;
	}


}