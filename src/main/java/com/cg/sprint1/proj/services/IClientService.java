package com.cg.sprint1.proj.services;

import java.util.List;
import java.util.Optional;

import com.cg.sprint1.proj.entities.*;
import com.cg.sprint1.proj.exceptions.*;

public interface IClientService {

	public boolean saveClient(String clientId, String password, String address, long phoneNumber);

	public Optional<Client> getClientByCLientId(String clientId) throws InvalidClientIdException;

	public Optional<Engineer> getEngineerById(int engineerId) throws InvalidEngineerIdException;

	public List<Engineer> getEngineersByDomain(String category);

	public boolean changeStatusOfComplaint(int complaintId, String status) throws PermissionDeniedException;

	public Client signIn(String clientId, String password) throws InvalidCredentialsException, InvalidClientIdException;

	public List<Client> getAll();
}
