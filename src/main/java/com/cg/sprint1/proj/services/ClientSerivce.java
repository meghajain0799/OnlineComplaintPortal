package com.cg.sprint1.proj.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.sprint1.proj.entities.Client;
import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.exceptions.InvalidClientIdException;
import com.cg.sprint1.proj.exceptions.InvalidCredentialsException;
import com.cg.sprint1.proj.exceptions.InvalidEngineerIdException;
import com.cg.sprint1.proj.exceptions.PermissionDeniedException;
import com.cg.sprint1.proj.repository.IClientRepository;
import com.cg.sprint1.proj.status.advice.Status;

@Service
public class ClientSerivce implements IClientService {
	@Autowired
	IClientRepository clientDao;

	@Transactional
	@Override
	public boolean saveClient(String clientId, String password, String address, long phoneNumber) {
		boolean methodCheck = false;
		clientDao.saveClient(clientId, password, address, phoneNumber);
		methodCheck = true;
		return methodCheck;
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Client> getClientByCLientId(String clientId) throws InvalidClientIdException {
		Optional<Client> c = clientDao.getClientByClientId(clientId);
		if (c.isPresent()) {
			return clientDao.getClientByClientId(clientId);
		} else {
			throw new InvalidClientIdException(" !!! Invalid Client ID Provided !!!");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Engineer> getEngineerById(int engineerId) throws InvalidEngineerIdException {
		Optional<Engineer> e = clientDao.getEngineerById(engineerId);
		if (e.isPresent()) {
			return clientDao.getEngineerById(engineerId);
		} else {
			throw new InvalidEngineerIdException("!!! Invalid Engineer ID Provided !!!");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Engineer> getEngineersByDomain(String category) {
		return clientDao.getEngineersByDomain(category);
	}

	@Transactional
	@Override
	public boolean changeStatusOfComplaint(int complaintId, String status) throws PermissionDeniedException {
		boolean methodCheck = false;
		if (status.equals(Status.CLOSED.toString())) {
			clientDao.changeStatusOfComplaint(complaintId, status);
			methodCheck = true;
		} else {
			throw new PermissionDeniedException("!!! PERMISSION DENIED !!!");
		}
		return methodCheck;
	}

	@Transactional(readOnly = true)
	@Override
	public Client signIn(String clientId, String password) throws InvalidCredentialsException, InvalidClientIdException{
		Optional<Client> client = clientDao.getClientByClientId(clientId);
		System.out.println(clientId);
		System.out.println(client.isPresent());
		String myPassword = clientDao.signIn(clientId);
		if (client.isPresent()) {
			if (myPassword.equals(password)) {
				return clientDao.getClientByClientId(clientId).get();
			} else {
				throw new InvalidCredentialsException(" !!! Invalid Credentials Given !!! ");
			}
		}
		else {
			System.out.println("coming here");
			throw new InvalidClientIdException("!!!");
		}
		
	}
	

	@Transactional(readOnly = true)
	@Override
	public List<Client> getAll(){
		return clientDao.findAll();
	}

	
	

}
