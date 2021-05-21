package com.cg.sprint1.proj.methodcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.cg.sprint1.proj.entities.Complaint;
import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.entities.Product;
import com.cg.sprint1.proj.exceptions.InValidComplaintIdException;
import com.cg.sprint1.proj.exceptions.PermissionDeniedException;
import com.cg.sprint1.proj.repository.IComplaintRepository;
import com.cg.sprint1.proj.services.ComplaintService;

@SpringBootTest
public class ComplaintServiceImplTest {

	@Autowired
	ComplaintService complaintService;

	@MockBean
	private IComplaintRepository complaintRepository;

	@Test
	public void testChangeComplaintStatus() throws PermissionDeniedException {

		when(complaintRepository.changeComplaintStatus(101, "CLOSED")).thenReturn(1);
		assertEquals(true, complaintService.changeComplaintStatus(101, "CLOSED"));
	}

	@Test
	public void testChangeComplaintStatusThrows() {

		assertThrows(PermissionDeniedException.class, () -> complaintService.changeComplaintStatus(101, "RESOLVED"));
	}

	@Test
	public void testGetClientAllComplaints() {
		
		List<Complaint> compalinList = new ArrayList<>();
		compalinList.add(new Complaint(104, "Mob108208308", "Stop", "OPEN", "client101", 201,LocalDate.of(2022, 01, 23), LocalDate.of(2024, 01, 23)));
		when(complaintRepository.getClientAllComplaints("client101")).thenReturn(compalinList);
		assertEquals(1, complaintService.getClientAllComplaints("client101").size());

	}

	@Test
	public void testGetClientAllOpenComplaints() {
		
		List<Complaint> compalinList = new ArrayList<>();
		compalinList.add(new Complaint(103, "AC108208308", "Stop", "OPEN", "client102", 202, LocalDate.of(2022, 01, 23),
				LocalDate.of(2022, 01, 27)));
		compalinList.add(new Complaint(104, "AC108208308", "Filter change", "OPEN", "client102", 201,
				LocalDate.of(2022, 04, 12), LocalDate.of(2022, 01, 16)));
		when(complaintRepository.getClientAllOpenComplaints("client102")).thenReturn(compalinList);
		assertEquals(2, complaintService.getClientAllOpenComplaints("client102").size());
	}

	@Test
	public void testGetEngineerByComplaintId() throws InValidComplaintIdException {
		
		Optional<Complaint> c1 = Optional.of(new Complaint(104, "AC101201301", "Leaking AC", "OPEN", "client101", 201,LocalDate.of(2022, 01, 13), LocalDate.of(2022, 07, 11)));
		Engineer e1 = new Engineer(201, "eng201", "Rahul", "AC");
		when(complaintRepository.getComplaintByComplaintId(104)).thenReturn(c1);
		when(complaintRepository.getEngineerByComplaintId(104)).thenReturn(e1);
		assertEquals(e1, complaintService.getEngineerByComplaintId(104));

	}

	@Test
	public void testGetEngineerByComplaintIdThrows() {
		
		Optional<Complaint> c1 = Optional.of(new Complaint(104, "AC101201301", "Leaking AC", "OPEN", "client101", 201,LocalDate.of(2022, 01, 13), LocalDate.of(2022, 07, 11)));
		when(complaintRepository.getComplaintByComplaintId(104)).thenReturn(c1);
		assertThrows(InValidComplaintIdException.class, () -> complaintService.getEngineerByComplaintId(102));
	}

	@Test
	public void testGetProductByComplaint() throws InValidComplaintIdException {
		
		Product p = new Product("AC101201301", "Daikin", "AC", LocalDate.of(2021, 03, 24), 2,LocalDate.of(2023, 03, 27), "client101");
		Optional<Complaint> c = Optional.of(new Complaint(103, "AC101201301", "Stop", "OPEN", "client101", 202,LocalDate.of(2022, 01, 23), LocalDate.of(2022, 01, 27)));
		when(complaintRepository.getComplaintByComplaintId(103)).thenReturn(c);
		when(complaintRepository.getProductByComplaintId(103)).thenReturn(p);
		assertEquals(p, complaintService.getProductByComplaintId(103));

	}

	@Test
	public void testGetProductByComplaintThrows() {
		
		Optional<Complaint> c = Optional.of(new Complaint(103, "AC101201301", "Stop", "OPEN", "client101", 202,LocalDate.of(2022, 01, 23), LocalDate.of(2022, 01, 27)));
		when(complaintRepository.getComplaintByComplaintId(104)).thenReturn(c);
		assertThrows(InValidComplaintIdException.class, () -> complaintService.getProductByComplaintId(102));
	}

}
