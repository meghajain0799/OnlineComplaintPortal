package com.cg.sprint1.proj.methodcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.cg.sprint1.proj.entities.Client;
import com.cg.sprint1.proj.entities.Complaint;
import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.exceptions.InvalidClientIdException;
import com.cg.sprint1.proj.exceptions.InvalidEngineerIdException;
import com.cg.sprint1.proj.exceptions.PermissionDeniedException;
import com.cg.sprint1.proj.repository.IClientRepository;
import com.cg.sprint1.proj.services.ClientSerivce;
import com.cg.sprint1.proj.status.advice.Status;

@SpringBootTest
public class ClientServiceImplTest {
	
	@Autowired
	 ClientSerivce clientService;
	
	@MockBean
	 IClientRepository repo;
	
	@Test
	public void testSaveClient()
	{
		Client client = new Client("client101","client101","tamil nadu",983456364);
		Mockito.when(repo.save(client)).thenReturn(client);
		assertEquals(true,clientService.saveClient("client101","client101","tamil nadu",983456364));
	}
	
	/*
	 * @Test public void testSignIn() {
	 * Mockito.when(repo.findById("client101")).thenReturn(Optional.of(new
	 * Client("client101","client101","tamil nadu",983456364)));
	 * 
	 * assertEquals(" *** Login Succesfull ***",clientService.signIn("client101",
	 * "client101")); }
	 */
	@Test
	public void getClientByClientIdTest() throws InvalidClientIdException
	{
		Mockito.when(repo.getClientByClientId("client101")).thenReturn(Optional.of(new Client("client101","client101","tamil nadu",983456364)));
		assertEquals(true,clientService.getClientByCLientId("client101").isPresent());
	}
	
	@Test
	public void getEngineerByIdTest() throws InvalidEngineerIdException 
	{
		Mockito.when(repo.getEngineerById(201)).thenReturn(Optional.of(new Engineer(201,"eng201","Rahul","AC")));
		assertEquals(true,clientService.getEngineerById(201).isPresent());
	}
	@Test
	public void changeStatusOfComplaintTest() throws PermissionDeniedException
	{
		when(repo.changeStatusOfComplaint(101, "CLOSED")).thenReturn(1);
		assertEquals(true, clientService.changeStatusOfComplaint(101, "CLOSED"));
	}
	
	
	@Test
	public void getEngineerByDomainTest()
	{
		Mockito.when(repo.getEngineersByDomain("AC")).thenReturn(Stream.of(new Engineer(202,"eng202","Shruthi","AC"),(new Engineer(203,"eng203","Meghana","AC"))).collect(Collectors.toList()));
		assertEquals(2,clientService.getEngineersByDomain("AC").size());
	}
	
	
	

}

