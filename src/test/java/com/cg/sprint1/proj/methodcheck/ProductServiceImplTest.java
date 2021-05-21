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
import com.cg.sprint1.proj.entities.Engineer;
import com.cg.sprint1.proj.entities.Product;
import com.cg.sprint1.proj.exceptions.InValidModelNumberException;
import com.cg.sprint1.proj.repository.IProductRepository;
import com.cg.sprint1.proj.services.ProductService;

@SpringBootTest
public class ProductServiceImplTest {
	
	@Autowired
	ProductService productService;

	@MockBean
	private IProductRepository productDao;
	
	@Test
	public void addProductTest()
	{	
		Mockito.when(productDao.addProduct("AC101201305", "GENERAL","AC",LocalDate.of(2022, 01, 13),1, LocalDate.of(2023, 01, 13),"client105")).thenReturn(1);
		assertEquals(true,productService.addProduct("AC101201305", "GENERAL","AC",LocalDate.of(2022, 01, 13),1,"client105"));
	}
	
	@Test
    void deleteByProductCategoryNameTest()
    {
        Mockito.when(productDao.deleteByProductCategoryName("AC")).thenReturn(1);
        assertEquals(true,productService.deleteByProductCategoryName("AC"));
       
    }
	
	@Test
	void getByProductCategoryNameTest() {
		Mockito.when(productDao.getByProductCategoryName("AC")).thenReturn(Stream.of(new Product("AC101201301", "Daikin", "AC", LocalDate.of(2021, 04, 28), 3, LocalDate.of(2024, 04, 28), "client101")).collect(Collectors.toList()));
        assertEquals(1,productService.getByProductCategoryName("AC").size());
	}
	
	
	@Test
	void getProductComplaintsTest() {
		Mockito.when(productDao.getProductComplaints("AC"))
        .thenReturn(Stream.of(new Complaint(101, "AC101201301", "Leaking AC", "OPEN", "client101", 201,
				LocalDate.of(2022, 01, 13), LocalDate.of(2022, 01, 23))).collect(Collectors.toList()));
        assertEquals(1,productService.getProductComplaints("AC").size());
	}
	
	@Test
	void getEngineersByProductTest() {
		Mockito.when(productDao.getEngineersByProduct("AC"))
        .thenReturn(Stream.of(new Engineer(201, "eng201", "Rahul", "AC")).collect(Collectors.toList()));
        assertEquals(1,productService.getEngineersByProduct("AC").size());
	}
	
	@Test
	void updateProductWarrantyTest() throws InValidModelNumberException{
		Optional<Product> p1 = Optional.of(new Product("AC101201301", "Daikin", "AC", LocalDate.of(2021, 04, 28), 3, LocalDate.of(2024, 04, 28), "client101"));
		when(productDao.findByModelNumber("AC101201301")).thenReturn(p1);
		when(productDao.updateProductWarranty("AC101201301",5,LocalDate.of(2021, 04, 28))).thenReturn(1);
		assertEquals(true,productService.updateProductWarranty("AC101201301",5));
	}
	
	@Test 
	public void updateProductWarrantyTestThrows() {
		
		Optional<Product> p = Optional.of(new Product("AC101201301", "Daikin", "AC", LocalDate.of(2021, 04, 28), 3, LocalDate.of(2024, 04, 28), "client101"));
		when(productDao.findByModelNumber("AC101201301")).thenReturn(p);
		assertThrows(InValidModelNumberException.class,()->productService.updateProductWarranty("AAACCC101201301",5));

	}

}
