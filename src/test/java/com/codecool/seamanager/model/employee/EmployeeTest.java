package com.codecool.seamanager.model.employee;

import com.codecool.seamanager.model.certificate.Certificate;
import org.junit.Before;
import org.junit.Test;

import static com.codecool.seamanager.model.employee.Gender.FEMALE;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.SECOND_ENGINEER;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {
	private Employee employee;

	@Before
	public void setUp() {
		employee = new Employee("John", "Doe", "01-01-1990", "123 Main St", THIRD_ENGINEER, MALE);
	}

	@Test
	public void testGetFirstName() {
		assertEquals("John", employee.getFirstName());
	}

	@Test
	public void testGetLastName() {
		assertEquals("Doe", employee.getLastName());
	}

	@Test
	public void testGetBirthDate() {
		assertEquals("01-01-1990", employee.getBirthDate());
	}

	@Test
	public void testGetAddress() {
		assertEquals("123 Main St", employee.getAddress());
	}

	@Test
	public void testGetRank() {
		assertEquals(THIRD_ENGINEER, employee.getRank());
	}

	@Test
	public void testSetRank() {
		employee.setRank(SECOND_ENGINEER);
		assertEquals(SECOND_ENGINEER, employee.getRank());
	}

	@Test
	public void testGetCertificates() {
		assertEquals(0, employee.getCertificates().size());
	}

	@Test
	public void testAddNewCertificate() {
		Certificate certificate = new Certificate(employee.getId(), "Seaman's Book", "30692CT", "13-01-2022", "13-01-2025");
		employee.addNewCertificate(certificate);
		assertEquals(1, employee.getCertificates().size());
		assertTrue(employee.getCertificates().contains(certificate));
	}

	@Test
	public void testNotEquals() {
		Employee employee2 = new Employee("Jane", "Doe", "01-01-1990", "123 Main St", THIRD_ENGINEER, FEMALE);
		assertNotEquals(employee, employee2);
	}
}