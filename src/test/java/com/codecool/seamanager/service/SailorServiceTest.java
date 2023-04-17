package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.email.EmailTakenException;
import com.codecool.seamanager.exceptions.email.SailorNotFoundException;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.repository.SailorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SailorServiceTest {
	@Mock
	private SailorRepository sailorRepository;

	@InjectMocks
	private SailorService sailorService;

	private Sailor sailor;

	@Before
	public void setUp() {
		sailor = new Sailor(
				"John",
				"Doe",
				LocalDate.of(1990, 1, 1),
				"+123456789",
				"123 Main St",
				"johndoe@gmail.com",
				THIRD_ENGINEER,
				MALE
		);
	}

	@Test
	public void testGetEmployees() {
		List<Sailor> expectedSailors = Arrays.asList(sailor);
		when(sailorRepository.findAll()).thenReturn(expectedSailors);
		List<Sailor> actualSailors = sailorService.getEmployees();
		assertEquals(expectedSailors, actualSailors);
	}

	@Test
	public void testGetEmployeeById() {
		when(sailorRepository.findById(1L)).thenReturn(Optional.of(sailor));
		Sailor actualSailor = sailorService.getEmployeeById(1L);
		assertEquals(sailor, actualSailor);
	}

	@Test(expected = SailorNotFoundException.class)
	public void testGetEmployeeByIdNotFound() {
		when(sailorRepository.findById(1L)).thenReturn(Optional.empty());
		sailorService.getEmployeeById(1L);
	}

	@Test
	public void addNewEmployee() {
		when(sailorRepository.findEmployeeByEmail("johndoe@gmail.com")).thenReturn(Optional.empty());
		sailorService.addNewEmployee(sailor);
		verify(sailorRepository).save(sailor);
	}

	@Test(expected = EmailTakenException.class)
	public void testAddNewEmployeeEmailTaken() {
		when(sailorRepository.findEmployeeByEmail("johndoe@gmail.com")).thenReturn(Optional.of(sailor));
		sailorService.addNewEmployee(sailor);
	}

	@Test
	public void testDeleteEmployee() {
		when(sailorRepository.existsById(1L)).thenReturn(true);
		when(sailorRepository.findById(1L)).thenReturn(Optional.of(sailor));
		sailorService.deleteEmployee(1L);
		verify(sailorRepository).deleteById(1L);
	}

	@Test(expected = SailorNotFoundException.class)
	public void testDeleteEmployeeNotFound() {
		when(sailorRepository.existsById(1L)).thenReturn(false);
		sailorService.deleteEmployee(1L);
	}

	@Test
	public void testUpdateEmployee(){
		when(sailorRepository.findById(1L)).thenReturn(Optional.of(sailor));
		Sailor updatedSailorDetails = new Sailor();
		updatedSailorDetails.setFirstName("New Name");
		updatedSailorDetails.setBirthDate(LocalDate.of(1992,2,13));
		ResponseEntity<Sailor> responseEntity = sailorService.updateEmployee(1L, updatedSailorDetails);
		sailorService.updateEmployee(1L, updatedSailorDetails);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("New Name", responseEntity.getBody().getFirstName());
		assertEquals(LocalDate.of(1992,2,13), responseEntity.getBody().getBirthDate());
	}
}
