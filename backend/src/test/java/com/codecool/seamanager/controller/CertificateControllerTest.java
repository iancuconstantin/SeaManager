package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.service.CertificateService;
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
import java.util.List;
import java.util.Optional;

import static com.codecool.seamanager.model.certificate.CertificateType.COC;
import static com.codecool.seamanager.model.certificate.CertificateType.SEAMANS_BOOK;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RunWith(MockitoJUnitRunner.class)
public class CertificateControllerTest {

	private static final String END_POINT_PATH = "/api/certificate";
	private Certificate certificate;
	private Sailor sailor;
	private MockMvc mockMvc;
	@Mock
	private CertificateService certificateService;
	@InjectMocks
	private CertificateController certificateController;

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
		sailor.setEmployeeId(1L);

		certificate = new Certificate(
				sailor,
				SEAMANS_BOOK,
				"49681DD",
				LocalDate.of(2023, 1, 11),
				LocalDate.of(2029, 1, 11)
		);

		mockMvc = MockMvcBuilders.standaloneSetup(certificateController).build();
	}

	@Test
	public void shouldReturnListOfCertificates() throws Exception {
		List<Certificate> certificates = List.of(certificate);
		when(certificateService.getCertificates()).thenReturn(certificates);

		mockMvc.perform(get(END_POINT_PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].owner.employeeId").value(sailor.getEmployeeId()))
				.andExpect(jsonPath("$[0].type").value(certificate.getType().toString()))
				.andExpect(jsonPath("$[0].serialNumber").value(certificate.getSerialNumber()))
				.andExpect(jsonPath("$[0].issueDate").value(certificate.getIssueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
				.andExpect(jsonPath("$[0].expiryDate").value(certificate.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
	}

	@Test
	public void shouldReturnCertificateById() throws Exception {
		Long certificateId = 1L;
		when(certificateService.getCertificateById(certificateId)).thenReturn(certificate);

		mockMvc.perform(get(END_POINT_PATH + "/" + certificateId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.owner.employeeId").value(sailor.getEmployeeId()))
				.andExpect(jsonPath("$.type").value(certificate.getType().toString()))
				.andExpect(jsonPath("$.serialNumber").value(certificate.getSerialNumber()))
				.andExpect(jsonPath("$.issueDate").value(certificate.getIssueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
				.andExpect(jsonPath("$.expiryDate").value(certificate.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
	}

	@Test
	public void shouldAddNewEmployee() throws Exception {
		String certificateJson = new ObjectMapper().findAndRegisterModules().writeValueAsString(certificate);

		mockMvc.perform(post(END_POINT_PATH).contentType(APPLICATION_JSON).content(certificateJson))
				.andExpect(status().isOk());

		verify(certificateService, times(1)).addNewCertificate(any(Certificate.class));
	}

	@Test
	public void shouldDeleteCertificateById() throws Exception {
		Long certificateId = 1L;

		mockMvc.perform(delete(END_POINT_PATH + "/" + certificateId))
				.andExpect(status().isOk());

		verify(certificateService, times(1)).deleteCertificate(certificateId);
	}

	@Test
	public void shouldUpdateCertificate() throws Exception {
		// Prepare test data
		Long certificateId = 1L;
		Certificate updatedCertificate = new Certificate(
				sailor,
				COC,
				"12345",
				LocalDate.of(2023, 1, 1),
				LocalDate.of(2029, 1, 1)
		);

		when(certificateService.updateCertificate(eq(certificateId), any(Certificate.class))).thenReturn(ResponseEntity.of(Optional.of(updatedCertificate)));

		String updatedCertificateJson = new ObjectMapper().findAndRegisterModules().writeValueAsString(updatedCertificate);
		mockMvc.perform(put(END_POINT_PATH + "/" + certificateId).contentType(APPLICATION_JSON).content(updatedCertificateJson))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.owner.employeeId").value(sailor.getEmployeeId()))
				.andExpect(jsonPath("$.type").value(updatedCertificate.getType().toString()))
				.andExpect(jsonPath("$.serialNumber").value(updatedCertificate.getSerialNumber()))
				.andExpect(jsonPath("$.issueDate").value(updatedCertificate.getIssueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
				.andExpect(jsonPath("$.expiryDate").value(updatedCertificate.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
		verify(certificateService, times(1)).updateCertificate(eq(certificateId), any(Certificate.class));
	}
}
