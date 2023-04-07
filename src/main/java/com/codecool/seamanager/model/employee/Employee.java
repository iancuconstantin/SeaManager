package com.codecool.seamanager.model.employee;

import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.vessel.Vessel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(
			name = "employee_id",
			updatable = false
	)
	private Long employeeId;
	@Column(
			name = "first_name",
			nullable = false,
			columnDefinition = "TEXT"
	)
	private String a_firstName;
	@Column(
			name = "last_name",
			nullable = false,
			columnDefinition = "TEXT"
	)
	private String b_lastName;
	@Column(
			name = "email",
			nullable = false,
			columnDefinition = "TEXT",
			unique = true
	)
	private String c_email;
	@Column(
			name = "birth_date",
			nullable = false,
			columnDefinition = "TEXT"
	)
	private String d_birthDate;
	@Column(
			name = "address",
			nullable = false,
			columnDefinition = "TEXT"
	)
	private String e_address;
	@Column(
			name = "contact_no",
			nullable = false,
			columnDefinition = "TEXT"
	)
	private String f_contactNo;
	@Column(
			name = "rank",
			nullable = false,
			columnDefinition = "TEXT"
	)
	@Enumerated(EnumType.STRING)
	private Rank g_rank;
	@Column(
			name = "gender",
			nullable = false,
			columnDefinition = "TEXT"
	)
	@Enumerated(EnumType.STRING)
	private Gender h_gender;

	@OneToMany(
			targetEntity = Certificate.class,
			mappedBy = "a_owner"
	)
	private List<Certificate> certificates;


	@ManyToOne
	@JoinColumn(
			name = "vessel_id",
			referencedColumnName = "id",
			columnDefinition = "BIGINT"
	)
	private Vessel i_vessel;

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
		this.i_vessel = null;
	}

	public void addNewCertificate(Certificate certificate) {
		certificates.add(certificate);
	}

	public Long getEmployeeId() {
		return employeeId;
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

	public void setA_firstName(String firstName) {
		this.a_firstName = firstName;
	}

	public void setB_lastName(String lastName) {
		this.b_lastName = lastName;
	}

	public void setC_email(String email) {
		this.c_email = email;
	}

	public void setD_birthDate(String birthDate) {
		this.d_birthDate = birthDate;
	}

	public void setE_address(String address) {
		this.e_address = address;
	}

	public void setF_contactNo(String contactNo) {
		this.f_contactNo = contactNo;
	}

	public void setG_rank(Rank rank) {
		this.g_rank = rank;
	}

	public void setH_gender(Gender gender) {
		this.h_gender = gender;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public void setCertificates(List<Certificate> certificates) {
		this.certificates = certificates;
	}

	@JsonIgnore
	public List<Certificate> getCertificates() {
		return certificates;
	}

	public Vessel getI_vessel() {
		return i_vessel;
	}

	public void setI_vessel(Vessel i_vessel) {
		this.i_vessel = i_vessel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return Objects.equals(employeeId, employee.employeeId) &&
				Objects.equals(a_firstName, employee.a_firstName) &&
				Objects.equals(b_lastName, employee.b_lastName) &&
				Objects.equals(c_email, employee.c_email) &&
				Objects.equals(d_birthDate, employee.d_birthDate) &&
				Objects.equals(e_address, employee.e_address) &&
				Objects.equals(f_contactNo, employee.f_contactNo) &&
				g_rank == employee.g_rank &&
				h_gender == employee.h_gender;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, a_firstName, b_lastName, c_email, d_birthDate, e_address, f_contactNo, g_rank, h_gender);
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + employeeId +
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