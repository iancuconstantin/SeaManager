package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.employee.Employee;
import com.codecool.seamanager.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.codecool.seamanager.model.employee.Gender.FEMALE;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.SECOND_ENGINEER;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

	private static final String END_POINT_PATH = "/api/employee";
	private Employee employee;
	private MockMvc mockMvc;
	@Mock
	private EmployeeService employeeService;
	@InjectMocks
	private EmployeeController employeeController;

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

		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
	}

	@Test
	public void shouldReturnListOfEmployees() throws Exception {
		List<Employee> employees = Arrays.asList(employee);
		when(employeeService.getEmployees()).thenReturn(employees);

		mockMvc.perform(get(END_POINT_PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].firstName").value(employee.getFirstName()))
				.andExpect(jsonPath("$[0].lastName").value(employee.getLastName()))
				.andExpect(jsonPath("$[0].email").value(employee.getEmail()))
				.andExpect(jsonPath("$[0].birthDate").value(employee.getBirthDate()))
				.andExpect(jsonPath("$[0].address").value(employee.getAddress()))
				.andExpect(jsonPath("$[0].contactNo").value(employee.getContactNo()))
				.andExpect(jsonPath("$[0].rank").value(employee.getRank().toString()))
				.andExpect(jsonPath("$[0].gender").value(employee.getGender().toString()));
	}

	@Test
	public void shouldReturnEmployeeById() throws Exception {
		Long employeeId = 1L;
		when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

		mockMvc.perform(get(END_POINT_PATH + "/" + employeeId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(employee.getLastName()))
				.andExpect(jsonPath("$.email").value(employee.getEmail()))
				.andExpect(jsonPath("$.birthDate").value(employee.getBirthDate()))
				.andExpect(jsonPath("$.address").value(employee.getAddress()))
				.andExpect(jsonPath("$.contactNo").value(employee.getContactNo()))
				.andExpect(jsonPath("$.rank").value(employee.getRank().toString()))
				.andExpect(jsonPath("$.gender").value(employee.getGender().toString()));
	}

	@Test
	public void shouldAddNewEmployee() throws Exception {
		String employeeJson = new ObjectMapper().writeValueAsString(employee);

		mockMvc.perform(post(END_POINT_PATH).contentType(APPLICATION_JSON).content(employeeJson))
				.andExpect(status().isOk());

		verify(employeeService, times(1)).addNewEmployee(any(Employee.class));
	}

	@Test
	public void shouldDeleteEmployeeById() throws Exception {
		Long employeeId = 1L;

		mockMvc.perform(delete(END_POINT_PATH + "/" + employeeId, employeeId))
				.andExpect(status().isOk());

		verify(employeeService, times(1)).deleteEmployee(employeeId);
	}

	@Test
	public void shouldUpdateEmployee() throws Exception {
		Long employeeId = 1L;
		Employee updatedEmployee = new Employee(
				"Jane",
				"Doe",
				"02-02-1991",
				"+987654321",
				"456 Main St",
				"janedoe@gmail.com",
				SECOND_ENGINEER,
				FEMALE
		);
		String employeeJson = new ObjectMapper().writeValueAsString(updatedEmployee);

		when(employeeService.updateEmployee(eq(employeeId), any(Employee.class))).thenReturn(ResponseEntity.of(Optional.of(updatedEmployee)));

		mockMvc.perform(put(END_POINT_PATH + "/" + employeeId).contentType(APPLICATION_JSON).content(employeeJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(updatedEmployee.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(updatedEmployee.getLastName()))
				.andExpect(jsonPath("$.email").value(updatedEmployee.getEmail()))
				.andExpect(jsonPath("$.birthDate").value(updatedEmployee.getBirthDate()))
				.andExpect(jsonPath("$.address").value(updatedEmployee.getAddress()))
				.andExpect(jsonPath("$.contactNo").value(updatedEmployee.getContactNo()))
				.andExpect(jsonPath("$.rank").value(updatedEmployee.getRank().toString()))
				.andExpect(jsonPath("$.gender").value(updatedEmployee.getGender().toString()));

		verify(employeeService, times(1)).updateEmployee(eq(employeeId), any(Employee.class));
	}
}
