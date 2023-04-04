package com.codecool.seamanager.model.employee;

import com.codecool.seamanager.model.certificate.Certificate;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Employee {
	@Id
	@GeneratedValue(
			strategy = GenerationType.AUTO
	)
	private Long id;
	@Column(name = "first_name")
	private String a_firstName;
	@Column(name = "last_name")
	private String b_lastName;
	@Column(name = "email")
	private String c_email;
	@Column(name = "birth_date")
	private String d_birthDate;
	@Column(name = "address")
	private String e_address;
	@Column(name = "contact_no")
	private String f_contactNo;
	@Column(name = "rank")
	@Enumerated(EnumType.STRING)
	private Rank g_rank;
	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private Gender h_gender;
	@OneToMany(targetEntity = Certificate.class,
	cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private List<Certificate> certificates;

	public Employee() {
	}

	public Employee(String firstName, String lastName, String birthDate, String contactNo, String address, String email, Rank rank, Gender gender) {
		this.a_firstName = firstName;
		this.b_lastName = lastName;
		this.c_email = email;
		this.d_birthDate = birthDate;
		this.e_address = address;
		this.f_contactNo = contactNo;
		this.g_rank = rank;
		this.h_gender = gender;
		this.certificates = new ArrayList<>();
	}

	public void addNewCertificate(Certificate certificate) {
		certificates.add(certificate);
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return a_firstName;
	}

	public String getLastName() {
		return b_lastName;
	}

	public String getEmail() {
		return c_email;
	}

	public String getBirthDate() {
		return d_birthDate;
	}

	public String getAddress() {
		return e_address;
	}

	public String getContactNo() {
		return f_contactNo;
	}

	public Rank getRank() {
		return g_rank;
	}

	public Gender getGender() {
		return h_gender;
	}

	public List<Certificate> getCertificates() {
		return certificates;
	}

	public void setRank(Rank rank) {
		this.g_rank = rank;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", firstName='" + a_firstName + '\'' +
				", lastName='" + b_lastName + '\'' +
				", email='" + c_email + '\'' +
				", birthDate='" + d_birthDate + '\'' +
				", address='" + e_address + '\'' +
				", contactNo='" + f_contactNo + '\'' +
				", rank=" + g_rank +
				", gender=" + h_gender +
				'}';
	}
}
