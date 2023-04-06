package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.email.EmailTakenException;
import com.codecool.seamanager.exceptions.email.EmployeeNotFoundException;
import com.codecool.seamanager.model.employee.Employee;
import com.codecool.seamanager.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeService employeeService;

	private Employee employee;

	@Before
	public void setUp() {
		employee = new Employee(
				"John",
				"Doe",
				"01-01-1990",
				"+123456789",
				"123 Main St",
				"johndoe@gmail.com",
				THIRD_ENGINEER,
				MALE
		);
	}

	@Test
	public void testGetEmployees() {
		List<Employee> expectedEmployees = Arrays.asList(employee);
		when(employeeRepository.findAll()).thenReturn(expectedEmployees);
		List<Employee> actualEmployees = employeeService.getEmployees();
		assertEquals(expectedEmployees, actualEmployees);
	}

	@Test
	public void testGetEmployeeById() {
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		Employee actualEmployee = employeeService.getEmployeeById(1L);
		assertEquals(employee, actualEmployee);
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testGetEmployeeByIdNotFound() {
		when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
		employeeService.getEmployeeById(1L);
	}

	@Test
	public void addNewEmployee() {
		when(employeeRepository.findEmployeeByEmail("johndoe@gmail.com")).thenReturn(Optional.empty());
		employeeService.addNewEmployee(employee);
		verify(employeeRepository).saveAndFlush(employee);
	}

	@Test(expected = EmailTakenException.class)
	public void testAddNewEmployeeEmailTaken() {
		when(employeeRepository.findEmployeeByEmail("johndoe@gmail.com")).thenReturn(Optional.of(employee));
		employeeService.addNewEmployee(employee);
	}

	@Test
	public void testDeleteEmployee() {
		when(employeeRepository.existsById(1L)).thenReturn(true);
		employeeService.deleteEmployee(1L);
		verify(employeeRepository).deleteById(1L);
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testDeleteEmployeeNotFound() {
		when(employeeRepository.existsById(1L)).thenReturn(false);
		employeeService.deleteEmployee(1L);
	}

	@Test
	public void testUpdateEmployee(){
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		Employee updatedEmployeeDetails = new Employee();
		updatedEmployeeDetails.setA_firstName("New Name");
		ResponseEntity<Employee> responseEntity = employeeService.updateEmployee(1L, updatedEmployeeDetails);
		employeeService.updateEmployee(1L, updatedEmployeeDetails);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("New Name", responseEntity.getBody().getFirstName());
	}
}
