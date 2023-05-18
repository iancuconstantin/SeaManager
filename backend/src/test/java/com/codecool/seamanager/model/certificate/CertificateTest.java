package com.codecool.seamanager.model.certificate;

import com.codecool.seamanager.model.employee.Sailor;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static com.codecool.seamanager.model.certificate.CertificateType.*;
import static com.codecool.seamanager.model.employee.Gender.FEMALE;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.junit.jupiter.api.Assertions.*;

public class CertificateTest {
	private Sailor sailor;
	private Certificate certificate;

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
				MALE,
				null
		);
		certificate = new Certificate(
				sailor,
				SEAMANS_BOOK,
				"12345",
				LocalDate.of(2021, 1, 1),
				LocalDate.of(2023, 1, 1)
		);
	}

	@Test
	public void testGetDescription() {
		assertEquals(SEAMANS_BOOK, certificate.getType());
	}

	@Test
	public void testGetSerialNumber() {
		assertEquals("12345", certificate.getSerialNumber());
	}

	@Test
	public void testGetIssueDate() {
		assertEquals((LocalDate.of(2021, 1, 1)), certificate.getIssueDate());
	}

	@Test
	public void testGetExpiryDate() {
		assertEquals((LocalDate.of(2023, 1, 1)), certificate.getExpiryDate());
	}

	@Test
	public void testSetOwner() {
		Sailor sailor2 = new Sailor(
				"Jane",
				"Doe",
				LocalDate.of(1990, 1, 1),
				"+123456789",
				"123 Main St",
				"johndoe@gmail.com",
				THIRD_ENGINEER,
				FEMALE,
				null
		);
		certificate.setOwner(sailor2);
		assertEquals(sailor2, certificate.getOwner());
	}

	@Test
	public void testSetDescription() {
		certificate.setType(FLAG_SEAMANS_BOOK);
		assertEquals(FLAG_SEAMANS_BOOK, certificate.getType());
	}

	@Test
	public void testSetSerialNumber() {
		certificate.setSerialNumber("45678T");
		assertEquals("45678T", certificate.getSerialNumber());
	}

	@Test
	public void testSetIssueDate() {
		certificate.setIssueDate(LocalDate.of(2021, 1, 1));
		assertEquals(LocalDate.of(2021, 1, 1), certificate.getIssueDate());
	}

	@Test
	public void testSetNullExpiryDate() {
		certificate.setExpiryDate(null);
		assertNull(certificate.getExpiryDate());
	}

	@Test
	public void testSetNotNullExpiryDate() {
		LocalDate date = LocalDate.of(2025, 2, 1);
		certificate.setExpiryDate(date);
		assertEquals(date, certificate.getExpiryDate());
	}

	@Test
	public void testNotEquals() {
		Certificate certificate2 = new Certificate(
				sailor,
				FLAG_ENDORSEMENT,
				"123456",
				LocalDate.of(2021, 1, 1),
				LocalDate.of(2023, 1, 1)
		);
		assertNotEquals(certificate, certificate2);
	}
}
