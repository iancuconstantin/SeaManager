package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.employee.Sailor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static com.codecool.seamanager.model.certificate.CertificateType.BT;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CertificateRepositoryTest {
	@Mock
	private CertificateRepository certificateRepository;
	private Certificate certificate;
	private Sailor sailor;

	@Before
	public void setUp(){
		sailor = new Sailor(
				"John",
				"Doe",
				LocalDate.of(1990, 1, 1),
				"+123456789",
				"123 Main St",
				"johndoe@gmail.com",
				THIRD_ENGINEER,
				MALE,
				null
		);

		certificate = new Certificate(
				sailor,
				BT,
				"015512000",
				LocalDate.of(2020,9,19),
				null
		);
	}

	@Test
	public void testFindCertificateBySerialNumber(){
		when(certificateRepository.findCertificateBySerialNumber(certificate.getSerialNumber())).thenReturn(Optional.of(certificate));

		Optional<Certificate> foundCertificate = certificateRepository.findCertificateBySerialNumber(certificate.getSerialNumber());
		assertTrue(foundCertificate.isPresent());
		assertEquals(foundCertificate.get(), certificate);

		verify(certificateRepository, times(1)).findCertificateBySerialNumber(certificate.getSerialNumber());

	}
}
