package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.service.SailorService;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class SailorControllerTest {

	private static final String END_POINT_PATH = "/api/employee";
	private Sailor sailor;
	private MockMvc mockMvc;
	@Mock
	private SailorService sailorService;
	@InjectMocks
	private SailorController sailorController;

	@Before
	public void setUp() {
		sailor = new Sailor(
				"John",
				"Doe",
				LocalDate.of(1990, 11, 1),
				"+123456789",
				"123 Main St",
				"johndoe@gmail.com",
				THIRD_ENGINEER,
				MALE,
				null
		);

		mockMvc = MockMvcBuilders.standaloneSetup(sailorController).build();
	}

	@Test
	public void shouldReturnListOfEmployees() throws Exception {
		List<Sailor> sailors = List.of(sailor);
		when(sailorService.getEmployees()).thenReturn(sailors);

		mockMvc.perform(get(END_POINT_PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].firstName").value(sailor.getFirstName()))
				.andExpect(jsonPath("$[0].lastName").value(sailor.getLastName()))
				.andExpect(jsonPath("$[0].email").value(sailor.getEmail()))
				.andExpect(jsonPath("$[0].birthDate").value(sailor.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
				.andExpect(jsonPath("$[0].address").value(sailor.getAddress()))
				.andExpect(jsonPath("$[0].contactNo").value(sailor.getContactNo()))
				.andExpect(jsonPath("$[0].rank").value(sailor.getRank().toString()))
				.andExpect(jsonPath("$[0].gender").value(sailor.getGender().toString()));
	}

	@Test
	public void shouldReturnEmployeeById() throws Exception {
		Long employeeId = 1L;
		when(sailorService.getEmployeeById(employeeId)).thenReturn(sailor);

		mockMvc.perform(get(END_POINT_PATH + "/" + employeeId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.firstName").value(sailor.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(sailor.getLastName()))
				.andExpect(jsonPath("$.email").value(sailor.getEmail()))
				.andExpect(jsonPath("$.birthDate").value(sailor.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
				.andExpect(jsonPath("$.address").value(sailor.getAddress()))
				.andExpect(jsonPath("$.contactNo").value(sailor.getContactNo()))
				.andExpect(jsonPath("$.rank").value(sailor.getRank().toString()))
				.andExpect(jsonPath("$.gender").value(sailor.getGender().toString()));
	}

	@Test
	public void shouldAddNewEmployee() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String employeeJson = mapper.writeValueAsString(sailor);

		mockMvc.perform(post(END_POINT_PATH).contentType(APPLICATION_JSON).content(employeeJson))
				.andExpect(status().isOk());

		verify(sailorService, times(1)).addNewEmployee(any(Sailor.class));
	}

	@Test
	public void shouldDeleteEmployeeById() throws Exception {
		Long employeeId = 1L;

		mockMvc.perform(delete(END_POINT_PATH + "/" + employeeId, employeeId))
				.andExpect(status().isOk());

		verify(sailorService, times(1)).deleteEmployee(employeeId);
	}

	@Test
	public void shouldUpdateEmployee() throws Exception {
		Long employeeId = 1L;
		Sailor updatedSailor = new Sailor(
				"Jane",
				"Doe",
				LocalDate.of(1991, 11, 1),
				"987654321",
				"456 Main St",
				"janedoe@gmail.com",
				SECOND_ENGINEER,
				FEMALE,
				null
		);
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String employeeJson = mapper.writeValueAsString(updatedSailor);

		when(sailorService.updateEmployee(eq(employeeId), any(Sailor.class))).thenReturn(ResponseEntity.of(Optional.of(updatedSailor)));
		mockMvc.perform(put(END_POINT_PATH + "/" + employeeId)
						.contentType(APPLICATION_JSON)
						.content(employeeJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(updatedSailor.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(updatedSailor.getLastName()))
				.andExpect(jsonPath("$.email").value(updatedSailor.getEmail()))
				.andExpect(jsonPath("$.birthDate").value(updatedSailor.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
				.andExpect(jsonPath("$.address").value(updatedSailor.getAddress()))
				.andExpect(jsonPath("$.contactNo").value(updatedSailor.getContactNo()))
				.andExpect(jsonPath("$.rank").value(updatedSailor.getRank().toString()))
				.andExpect(jsonPath("$.gender").value(updatedSailor.getGender().toString()));

		verify(sailorService, times(1)).updateEmployee(eq(employeeId), any(Sailor.class));
	}

	private int[] getListOfValuesFromDateInOrderYearMonthDay(String date){
		int[] listOfValues = new int[3];
		String[] split = date.split("-");
		for (int i = 0; i < split.length; i++) {
			listOfValues[i] = Integer.parseInt(split[i]);
		}

		return listOfValues;
	}
}
