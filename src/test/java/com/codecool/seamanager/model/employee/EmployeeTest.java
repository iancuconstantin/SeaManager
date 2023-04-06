package com.codecool.seamanager.model.employee;

import com.codecool.seamanager.model.certificate.Certificate;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.codecool.seamanager.model.employee.Gender.FEMALE;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.SECOND_ENGINEER;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {
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
	public void testGetEmail() {
		assertEquals("johndoe@gmail.com", employee.getEmail());
	}

	@Test
	public void testGetContactNo() {
		assertEquals("+123456789", employee.getContactNo());
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
		employee.setG_rank(SECOND_ENGINEER);
		assertEquals(SECOND_ENGINEER, employee.getRank());
	}

	@Test
	public void testSetGender() {
		employee.setH_gender(FEMALE);
		assertEquals(FEMALE, employee.getGender());
	}

	@Test
	public void testSetFirstName() {
		employee.setA_firstName("Test");
		assertEquals("Test", employee.getFirstName());
	}

	@Test
	public void testGetId() {
		assertNull(employee.getEmployeeId());
	}

	@Test
	public void testSetLastName() {
		employee.setB_lastName("Test");
		assertEquals("Test", employee.getLastName());
	}

	@Test
	public void testSetEmail() {
		employee.setC_email("test@gmail.com");
		assertEquals("test@gmail.com", employee.getEmail());
	}

	@Test
	public void testSetBirthDate() {
		employee.setD_birthDate("02-02-1990");
		assertEquals("02-02-1990", employee.getBirthDate());
	}

	@Test
	public void testSetAddress() {
		employee.setE_address("456 Main St");
		assertEquals("456 Main St", employee.getAddress());
	}

	@Test
	public void testSetContactNo() {
		employee.setF_contactNo("+987654321");
		assertEquals("+987654321", employee.getContactNo());
	}

	@Test
	public void testSetCertificates() {
		List<Certificate> certificates = new ArrayList<>();
		certificates.add(new Certificate(employee, "Certificate 1", "12345", "01-01-2021", "01-01-2023"));
		certificates.add(new Certificate(employee, "Certificate 2", "67890", "01-01-2022", "01-01-2024"));
		employee.setCertificates(certificates);
		assertEquals(2, employee.getCertificates().size());
		assertTrue(employee.getCertificates().containsAll(certificates));
	}

	@Test
	public void testGetCertificates() {
		assertEquals(0, employee.getCertificates().size());
	}

	@Test
	public void testAddNewCertificate() {
		Certificate certificate = new Certificate(
				employee,
				"Seaman's Book",
				"30123SS",
				"13-01-2022",
				"13-01-2025"
		);
		employee.addNewCertificate(certificate);
		assertEquals(1, employee.getCertificates().size());
		assertTrue(employee.getCertificates().contains(certificate));
	}

	@Test
	public void testNotEquals() {
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
		assertNotEquals(employee, employee2);
	}

	@Test
	public void testCertificatesAddedToEmployeeOnConstruction(){
		Certificate certificate = new Certificate(
				employee,
				"Passport",
				"571231212",
				"12-12-2018",
				"12-12-2023"
		);

		Certificate certificate2 = new Certificate(
				employee,
				"US VISA",
				"12345",
				"12-12-2022",
				"12-12-2025"
		);

		assertEquals(2, employee.getCertificates().size());
		assertEquals(certificate, employee.getCertificates().get(0));
		assertEquals(certificate2, employee.getCertificates().get(1));
	}
}