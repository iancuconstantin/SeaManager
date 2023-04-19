package com.codecool.seamanager.service;


import com.codecool.seamanager.exceptions.certificate.CertificateNotFoundException;
import com.codecool.seamanager.exceptions.certificate.CertificateSerialNumberDuplicationException;
import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.repository.CertificateRepository;
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

import static com.codecool.seamanager.model.certificate.CertificateType.ARPA;
import static com.codecool.seamanager.model.certificate.CertificateType.SEAMANS_BOOK;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CertificateServiceTest {

	@Mock
	private CertificateRepository certificateRepository;
	@InjectMocks
	private CertificateService certificateService;
	private Certificate certificate;
	private Sailor sailor;
	private TestRestTemplate restTemplate;

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
		sailor.setEmployeeId(1L);

		certificate = new Certificate(
				sailor,
				SEAMANS_BOOK,
				"49681DD",
				LocalDate.of(2023,1,11),
				LocalDate.of(2029,1,11)
		);

		restTemplate = new TestRestTemplate(new RestTemplateBuilder());
	}

	@Test
	public void testGetCertificates() {
		List<Certificate> expectedCertificates = Arrays.asList(certificate);
		when(certificateRepository.findAll()).thenReturn(expectedCertificates);
		List<Certificate> actualCertificates = certificateService.getCertificates();
		assertEquals(expectedCertificates, actualCertificates);
	}

	@Test
	public void testGetCertificateById() {
		when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));
		Certificate actualCertificate = certificateService.getCertificateById(1L);
		assertEquals(certificate, actualCertificate);
	}

	@Test(expected = CertificateNotFoundException.class)
	public void testGetCertificateByIdNotFound() {
		when(certificateRepository.findById(1L)).thenReturn(Optional.empty());
		certificateService.getCertificateById(1L);
	}

	@Test
	public void addNewCertificate() {
		when(certificateRepository.findCertificateBySerialNumber(certificate.getSerialNumber())).thenReturn(Optional.empty());
		certificateService.addNewCertificate(certificate);
		verify(certificateRepository).saveAndFlush(certificate);
	}

	@Test(expected = CertificateSerialNumberDuplicationException.class)
	public void testAddNewCertificateWithDuplicateSerialNumber() {
		when(certificateRepository.findCertificateBySerialNumber(certificate.getSerialNumber())).thenReturn(Optional.of(certificate));
		certificateService.addNewCertificate(certificate);
	}

	@Test
	public void testDeleteCertificate() {
		when(certificateRepository.existsById(1L)).thenReturn(true);
		certificateService.deleteCertificate(1L);
		verify(certificateRepository).deleteById(1L);
	}

	@Test(expected = CertificateNotFoundException.class)
	public void testDeleteCertificateNotFound() {
		when(certificateRepository.existsById(1L)).thenReturn(false);
		certificateService.deleteCertificate(1L);
	}

	@Test
	public void testUpdateCertificate() {
		//when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));
		Certificate updatedCertificateDetails = new Certificate(
				sailor,
				ARPA,
				"49AA681DD",
				LocalDate.of(2020, 9, 19),
				LocalDate.of(2024, 9, 19)
		);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create a HttpEntity object with the updatedVesselDetails and headers
		HttpEntity<Certificate> request = new HttpEntity<>(updatedCertificateDetails, headers);

		// Send a PUT request to the updateVessel endpoint using RestTemplate
		ResponseEntity<Certificate> responseEntity = restTemplate.exchange(
				"http://localhost:8080/api/certificate/{id}",
				HttpMethod.PUT,
				request,
				Certificate.class,
				1L
		);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Certificate updatedCertificate = responseEntity.getBody();
		assertEquals(ARPA, updatedCertificate.getType());
		assertEquals(LocalDate.of(2020, 9, 19), updatedCertificate.getIssueDate());
		assertEquals(LocalDate.of(2024, 9, 19), updatedCertificate.getExpiryDate());
	}
}
