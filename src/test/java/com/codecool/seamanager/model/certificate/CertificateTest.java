package com.codecool.seamanager.model.certificate;

import com.codecool.seamanager.model.employee.Employee;
import org.junit.Before;
import org.junit.Test;

import static com.codecool.seamanager.model.employee.Gender.FEMALE;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.junit.jupiter.api.Assertions.*;

public class CertificateTest {
	private Employee employee;
	private Certificate certificate;

	@Before
	public void setUp(){
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
		certificate = new Certificate(
				employee,
				"Seaman's Book",
				"12345",
				"01-01-2022",
				"01-01-2025"
				);
	}

	@Test
	public void testGetDescription(){
		assertEquals("Seaman's Book", certificate.getDescription());
	}

	@Test
	public void testGetSerialNumber(){
		assertEquals("12345", certificate.getSerialNumber());
	}

	@Test
	public void testGetIssueDate(){
		assertEquals("01-01-2022", certificate.getIssueDate());
	}

	@Test
	public void testGetExpiryDate(){
		assertEquals("01-01-2025", certificate.getExpiryDate());
	}

	@Test
	public void testSetOwner(){
		Employee employee2 = new Employee(
				"Jane",
				"Doe",
				"01-01-1990",
				"+123456789",
				"123 Main St",
				"johndoe@gmail.com",
				THIRD_ENGINEER,
				FEMALE
		);
		certificate.setA_owner(employee2);
		assertEquals(employee2, certificate.getOwner());
	}
	@Test
	public void testSetDescription(){
		certificate.setB_description("Passport");
		assertEquals("Passport", certificate.getDescription());
	}
	@Test
	public void testSetSerialNumber(){
		certificate.setC_serialNumber("45678T");
		assertEquals("45678T", certificate.getSerialNumber());
	}

	@Test
	public void testSetIssueDate(){
		certificate.setD_issueDate("02-03-2023");
		assertEquals("02-03-2023", certificate.getIssueDate());
	}

	@Test
	public void testSetNullExpiryDate(){
		certificate.setE_expiryDate(null);
		assertNull(certificate.getExpiryDate());
	}

	@Test
	public void testSetNotNullExpiryDate(){
		certificate.setE_expiryDate("04-12-2028");
		assertEquals("04-12-2028", certificate.getExpiryDate());
	}

	@Test
	public void testNotEquals(){
		Certificate certificate2 = new Certificate(
				employee,
				"Passport",
				"123456",
				"01-01-2022",
				"01-01-2025"
		);
		assertNotEquals(certificate, certificate2);
	}
}
