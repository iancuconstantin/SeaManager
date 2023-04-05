package com.codecool.seamanager.model;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Entity
@Table(name = "employee")
public class Employee {
	@ManyToOne
	@JoinColumn(name = "vessel_id")
	private Vessel vessel;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(name = "firstName")
	private String firstName;
	@NotNull
	@Column(name = "lastName")
	private String lastName;
	@NotNull
	@Column(name = "birthDate")
	private Date birthDate;
	@NotNull
	@Column(name = "address")
	private String address;
	@NotNull
	@Column(name = "rank")
	@Enumerated(EnumType.STRING)
	private Rank rank;

	@NotNull
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "certificate_id")
	private List<Certificate> certificates;

	public void addNewCertificate(Certificate certificate){
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

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setVessel(Vessel vessel) {
		this.vessel = vessel;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCertificates(List<Certificate> certificates) {
		this.certificates = certificates;
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
