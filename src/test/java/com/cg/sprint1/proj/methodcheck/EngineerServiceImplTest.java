package com.cg.sprint1.proj.methodcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.cg.sprint1.proj.entities.Complaint;
import com.cg.sprint1.proj.exceptions.InValidComplaintIdException;
import com.cg.sprint1.proj.exceptions.PermissionDeniedException;
import com.cg.sprint1.proj.repository.IEngineerRepository;
import com.cg.sprint1.proj.services.EngineerService;

@SpringBootTest
public class EngineerServiceImplTest {

	@Autowired
	EngineerService engineerService;
	
	@MockBean
	IEngineerRepository engineerDao;

	@Test
	public void testGetAllOpenComplaints() {
		
		Mockito.when(engineerDao.getAllOpenComplaints(201)).thenReturn(Stream.of(new Complaint(101, "AC101201301", "Leaking AC", "OPEN", "client101", 201,
				LocalDate.of(2022, 01, 13), LocalDate.of(2022, 01, 23))).collect(Collectors.toList()));
        assertEquals(1,engineerService.getAllOpenComplaints(201).size());
	}

	@Test
	void testGetResolvedComplaintsTest() {
		
		Mockito.when(engineerDao.getResolvedComplaints(201))
        .thenReturn(Stream.of(new Complaint(101, "AC101201301", "Leaking AC", "RESOLVED", "client101", 201,
				LocalDate.of(2022, 01, 13), LocalDate.of(2022, 01, 23))).collect(Collectors.toList()));
        assertEquals(1,engineerService.getResolvedComplaints(201).size());

	}


	@Test
	void testGetResolvedComplaintsByDateTest() {
		
		Mockito.when(engineerDao.getResolvedComplaintsByDate(201, LocalDate.of(2022, 01, 16)))
        .thenReturn(Stream.of(new Complaint(101, "AC101201301", "Leaking AC", "RESOLVED", "client101", 201,
				LocalDate.of(2022, 01, 13), LocalDate.of(2022, 01, 23))).collect(Collectors.toList()));
        assertEquals(1,engineerService.getResolvedComplaintsByDate(201, LocalDate.of(2022, 01, 16)).size());
     
	}

	@Test
	void testGetComplaints() {

		Mockito.when(engineerDao.getComplaints(201, "OPEN"))
        .thenReturn(Stream.of(new Complaint(101, "AC101201301", "Leaking AC", "OPEN", "client101", 201,
				LocalDate.of(2022, 01, 13), LocalDate.of(2022, 01, 23))).collect(Collectors.toList()));
        assertEquals(1,engineerService.getComplaints(201, "OPEN").size());

	}

	@Test
	public void testChangeComplaintStatus() throws InValidComplaintIdException, PermissionDeniedException {
		
		Optional<Complaint> c1 = Optional.of(new Complaint(104, "AC101201301", "Leaking AC", "OPEN", "client101", 201,LocalDate.of(2022, 01, 13), LocalDate.of(2022, 07, 11)));
		when(engineerDao.getComplaintByComplaintId(104)).thenReturn(c1);
		when(engineerDao.changeComplaintStatus(104,"RESOLVED")).thenReturn(1);
		assertEquals(true,engineerService.changeComplaintStatus(104, "RESOLVED"));
	}
	
	@Test 
	public void testChangeComplaintStatusThrows() {
		
		Optional<Complaint> c = Optional.of(new Complaint(104, "AC101201301", "Leaking AC", "OPEN", "client101", 201,LocalDate.of(2022, 01, 13), LocalDate.of(2022, 07, 11)));
		when(engineerDao.getComplaintByComplaintId(104)).thenReturn(c);
		assertThrows(InValidComplaintIdException.class,()->engineerService.changeComplaintStatus(102,"RESOLVED"));

	}
	
	@Test 
	public void testChangeComplaintStatusThrowsPermissionDeniedException() {
		
		Optional<Complaint> c = Optional.of(new Complaint(104, "AC101201301", "Leaking AC", "OPEN", "client101", 201,LocalDate.of(2022, 01, 13), LocalDate.of(2022, 07, 11)));
		when(engineerDao.getComplaintByComplaintId(104)).thenReturn(c);
	    assertThrows(PermissionDeniedException.class,()->engineerService.changeComplaintStatus(104,"CLOSED"));

	}
	
	@Test
	void testGetOpenComplaintsOrderByPriorityTest() {
		
		Mockito.when(engineerDao.getOpenComplaintsOrderByPriority(201))
        .thenReturn(Stream.of(new Complaint(101, "AC101201301", "Leaking AC", "OPEN", "client101", 201,
				LocalDate.of(2022, 01, 13), LocalDate.of(2022, 01, 23))).collect(Collectors.toList()));
        assertEquals(1,engineerService.getOpenComplaintsOrderByPriority(201).size());

	}
	
}
