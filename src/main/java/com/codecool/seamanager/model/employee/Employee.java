package com.codecool.seamanager.model.employee;

import com.codecool.seamanager.model.certificate.Certificate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Employee {
	private final String id;
	private final String firstName;
	private final String lastName;
	private final String birthDate;
	private final String address;
	private Rank rank;
	private Gender gender;
	private final List<Certificate> certificates;

	public Employee(String firstName, String lastName, String birthDate, String address, Rank rank, Gender gender) {
		this.id = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.address = address;
		this.rank = rank;
		this.certificates = new ArrayList<>();
	}

	public void addNewCertificate(Certificate certificate) {
		certificates.add(certificate);
	}

	public List<Certificate> getCertificates() {
		return certificates;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id='" + id + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", birthDate='" + birthDate + '\'' +
				", address='" + address + '\'' +
				", certificates=" + certificates +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return id.equals(employee.id) && firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && birthDate.equals(employee.birthDate) && address.equals(employee.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, birthDate, address);
	}
}
