package com.cg.sprint1.proj.methodcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.cg.sprint1.proj.entities.Complaint;
import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.entities.Product;
import com.cg.sprint1.proj.exceptions.InValidDomainException;
import com.cg.sprint1.proj.exceptions.InvalidEngineerIdException;
import com.cg.sprint1.proj.repository.IAdminRepository;
import com.cg.sprint1.proj.services.AdminService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class AdminServiceImplTest{

	@Autowired
	AdminService adminService;
	
	@MockBean
    IAdminRepository adminRepository;
	
    @Test
	public void addEngineerTest()
	{
		Mockito.when(adminRepository.addEngineer(409,"eng209","ajith","Ac")).thenReturn(1);
		assertEquals(true,adminService.addEngineer(409,"eng209","ajith","Ac"));
	}
	
	@Test
	public void changeDomainTest() throws InValidDomainException,InvalidEngineerIdException
	{

		Optional<Engineer> engineer = Optional.of(new Engineer(409,"eng209","ajith","AC"));
		when(adminRepository.getEngineerbyId(409)).thenReturn(engineer);
		when(adminRepository.changeDomain(409,"TV")).thenReturn(1);
		assertEquals(true,adminService.changeDomain(409,"TV"));
	
	}
	
	@Test
	public void removeEngineerTest() throws InvalidEngineerIdException {
		Optional<Engineer> engineer = Optional.of(new Engineer(409,"eng209","ajith","AC"));
		when(adminRepository.getEngineerbyId(409)).thenReturn(engineer);
		Mockito.when(adminRepository.removeEngineer(409)).thenReturn(1);
		assertEquals(true,adminService.removeEngineer(409));
	}
	
	@Test
	public void removeEngineerTestThrows() {
		Optional<Engineer> engineer = Optional.of(new Engineer(409,"eng209","ajith","AC"));
		when(adminRepository.getEngineerbyId(409)).thenReturn(engineer);
		assertThrows(InvalidEngineerIdException.class,()->adminService.removeEngineer(407));
	}
	
	
	@Test  
	public void getComplaintsByProductsTest() {
		List<Complaint> c = new ArrayList<>();
				c.add(new Complaint(103,"AC101201301","Stop","OPEN","client101",202,LocalDate.of(2022, 01,23),LocalDate.of(2022,01,27)));
		when(adminRepository.getComplaintsByProducts("AC")).thenReturn(c);
		assertEquals(c,adminService.getComplaintsByProducts("AC"));
			}
	
	@Test  
	public void getComplaintsTest() {
	
		List<Complaint> c = new ArrayList<>();
				c.add(new Complaint(103,"AC101201301","Stop","OPEN","client101",202,LocalDate.of(2022, 01,23),LocalDate.of(2022,01,27)));
		when(adminRepository.getComplaints("OPEN","AC")).thenReturn(c);
		assertEquals(c,adminService.getComplaints("OPEN","AC"));
			}
	
	}