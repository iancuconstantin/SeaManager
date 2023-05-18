package com.codecool.seamanager.model.employee;

import com.codecool.seamanager.model.certificate.Certificate;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.codecool.seamanager.model.certificate.CertificateType.*;
import static com.codecool.seamanager.model.employee.Gender.FEMALE;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.SECOND_ENGINEER;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.junit.jupiter.api.Assertions.*;

public class SailorTest {
	private Sailor sailor;

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
	}

	@Test
	public void testGetFirstName() {
		assertEquals("John", sailor.getFirstName());
	}

	@Test
	public void testGetLastName() {
		assertEquals("Doe", sailor.getLastName());
	}

	@Test
	public void testGetBirthDate() {
		assertEquals("1990-11-01", sailor.getBirthDate().toString());
	}

	@Test
	public void testGetEmail() {
		assertEquals("johndoe@gmail.com", sailor.getEmail());
	}

	@Test
	public void testGetContactNo() {
		assertEquals("+123456789", sailor.getContactNo());
	}

	@Test
	public void testGetAddress() {
		assertEquals("123 Main St", sailor.getAddress());
	}

	@Test
	public void testGetRank() {
		assertEquals(THIRD_ENGINEER, sailor.getRank());
	}

	@Test
	public void testSetRank() {
		sailor.setRank(SECOND_ENGINEER);
		assertEquals(SECOND_ENGINEER, sailor.getRank());
	}

	@Test
	public void testSetGender() {
		sailor.setGender(FEMALE);
		assertEquals(FEMALE, sailor.getGender());
	}

	@Test
	public void testSetFirstName() {
		sailor.setFirstName("Test");
		assertEquals("Test", sailor.getFirstName());
	}

	@Test
	public void testGetId() {
		assertNull(sailor.getEmployeeId());
	}

	@Test
	public void testSetLastName() {
		sailor.setLastName("Test");
		assertEquals("Test", sailor.getLastName());
	}

	@Test
	public void testSetEmail() {
		sailor.setEmail("test@gmail.com");
		assertEquals("test@gmail.com", sailor.getEmail());
	}

	@Test
	public void testSetBirthDate() {
		sailor.setBirthDate(LocalDate.of(1992, 11, 1));
		assertEquals(LocalDate.of(1992, 11, 1), sailor.getBirthDate());
	}

	@Test
	public void testSetAddress() {
		sailor.setAddress("456 Main St");
		assertEquals("456 Main St", sailor.getAddress());
	}

	@Test
	public void testSetContactNo() {
		sailor.setContactNo("+987654321");
		assertEquals("+987654321", sailor.getContactNo());
	}

	@Test
	public void testSetCertificates() {
		Set<Certificate> certificates = new HashSet<>();
		certificates.add(
				new Certificate(
						sailor,
						PASSPORT,
						"12345",
						LocalDate.of(2021, 1, 1),
						LocalDate.of(2023, 1, 1)
				)
		);
		certificates.add(
				new Certificate(
						sailor,
						PASSPORT,
						"12345",
						LocalDate.of(2024, 3, 1),
						LocalDate.of(2026, 3, 1)
				)
		);
		assertEquals(2, sailor.getCertificates().size());
		assertTrue(sailor.getCertificates().containsAll(certificates));
	}

	@Test
	public void testGetCertificates() {
		assertEquals(0, sailor.getCertificates().size());
	}

	@Test
	public void testAddNewCertificate() {
		Certificate certificate = new Certificate(
				sailor,
				SEAMANS_BOOK,
				"30123SS",
				LocalDate.of(2021, 1, 1),
				LocalDate.of(2023, 1, 1)
		);
		sailor.addNewCertificate(certificate);
		assertEquals(1, sailor.getCertificates().size());
		assertTrue(sailor.getCertificates().contains(certificate));
	}

	@Test
	public void testNotEquals() {
		Sailor sailor2 = new Sailor(
				"Jane",
				"Doe",
				LocalDate.of(1992, 11, 1),
				"+123456789",
				"123 Main St",
				"johndoe@gmail.com",
				THIRD_ENGINEER,
				FEMALE,
				null
		);
		assertNotEquals(sailor, sailor2);
	}

	@Test
	public void testCertificatesAddedToEmployeeOnConstruction() {
		Certificate certificate = new Certificate(
				sailor,
				PASSPORT,
				"571231212",
				LocalDate.of(2021, 1, 1),
				LocalDate.of(2023, 1, 1)
		);

		Certificate certificate2 = new Certificate(
				sailor,
				US_VISA,
				"12345",
				LocalDate.of(2021, 1, 1),
				LocalDate.of(2023, 1, 1)
		);

		assertEquals(2, sailor.getCertificates().size());
		assertTrue(sailor.getCertificates().contains(certificate));
		assertTrue(sailor.getCertificates().contains(certificate2));
	}
}