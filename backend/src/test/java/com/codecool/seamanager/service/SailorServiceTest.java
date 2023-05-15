package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.sailor.EmailTakenException;
import com.codecool.seamanager.exceptions.sailor.SailorNotFoundException;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.repository.SailorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.codecool.seamanager.model.employee.Gender.FEMALE;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.SECOND_ENGINEER;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SailorServiceTest {
	@Mock
	private SailorRepository sailorRepository;
	@InjectMocks
	private SailorService sailorService;
	private Sailor sailor;
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() {
		sailor = new Sailor(
				"John",
				"Doe",
				LocalDate.of(1990, 1, 1),
				"123456789",
				"123 Main St",
				"johndoe@gmail.com",
				THIRD_ENGINEER,
				MALE,
				null
		);

		restTemplate = new TestRestTemplate(new RestTemplateBuilder());
	}

	@Test
	public void testGetEmployees() {
		List<Sailor> expectedSailors = List.of(sailor);
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
		Sailor updatedSailorDetails = new Sailor(
				"Gigel",
				"Dorel",
				LocalDate.of(1991, 4, 20),
				"123456788",
				"123 Strada",
				"johndoe@company.co.uk",
				SECOND_ENGINEER,
				FEMALE,
				null
		);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Sailor> request = new HttpEntity<>(updatedSailorDetails, headers);
		ResponseEntity<Sailor> responseEntity = restTemplate.exchange(
				"http://localhost:8080/api/employee/{id}",
				HttpMethod.PUT,
				request,
				Sailor.class,
				1L
		);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Sailor updatedSailor = responseEntity.getBody();
		assertEquals("Gigel", updatedSailor.getFirstName());
		assertEquals(LocalDate.of(1991,4,20), updatedSailor.getBirthDate());
	}
}
