package com.codecool.seamanager.model.employee;

import com.codecool.seamanager.constraints.BirthDate;
import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.contract.Contract;
import com.codecool.seamanager.model.voyage.Voyage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(name = "email_uk", columnNames = {"email"})
		}
)
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"certificates", "currentVoyage"})
public class Sailor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(
			name = "employee_id",
			updatable = false
	)
	private Long employeeId;
	@NotBlank(message = "First name is required")
	@Size(
			min = 2,
			max = 25,
			message = "First name should be between 2 and 25 characters long"
	)
	@Column(
			columnDefinition = "TEXT"
	)
	private String firstName;
	@NotBlank(message = "Last name is required")
	@Size(
			min = 2,
			max = 15,
			message = "Last name should be between 2 and 15 characters long"
	)
	@Column(
			columnDefinition = "TEXT"
	)
	private String lastName;
	@Email(
			message = "Not a valid email"
	)
	@Column(
			columnDefinition = "TEXT"
	)
	private String email;
	@BirthDate(
			message = "Age must be greater or equal than 18"
	)
	@Past(
			message = "The birth date must be in the past."
	)
	@Column(
			columnDefinition = "DATE"
	)
	@JsonFormat(
			shape = STRING,
			pattern = "yyyy-MM-dd"
	)
//	@JsonDeserialize(using = LocalDateDeserializer.class)
//	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate birthDate;
	private int age;
	@NotBlank
	@Size(
			min = 5,
			max = 150,
			message = "Address should be between 5 and 150 characters long."
	)
	@Column(
			columnDefinition = "TEXT"
	)
	private String address;
	@Column(
			columnDefinition = "TEXT"
	)
	@Pattern(
			regexp = "^\\d{9,15}",
			message = "Incorrect contact number format. Accepting only digits (min 9, max 15)."
	)
	private String contactNo;
	@NotNull
	@Column(columnDefinition = "TEXT")
	@Enumerated(
			EnumType.STRING
	)
	@NotNull
//	@ValidEnum(
//			enumClass = Rank.class,
//			message = "Invalid rank."
//	) //TODO - not working as intended
	private Rank rank;
	@NotNull
	@Column(columnDefinition = "TEXT")
	@Enumerated(
			EnumType.STRING
	)
	private Gender gender;

	@OneToMany(
			targetEntity = Certificate.class,
			mappedBy = "owner"
	)
	private Set<Certificate> certificates;
	@OneToMany(
			targetEntity = Contract.class,
			mappedBy = "owner"
	)
	private Set<Contract> contracts;
	@Future(
			message = "Readiness date must be in the future."
	)
	@Column(
			columnDefinition = "DATE"
	)
	@JsonFormat(
			shape = STRING,
			pattern = "yyyy-MM-dd"
	)
	private LocalDate readinessDate;
	@ManyToOne
	@JoinTable(
			name = "sailor_voyage",
			joinColumns = @JoinColumn(name = "employee_id"),
			inverseJoinColumns = @JoinColumn(
					referencedColumnName = "voyageId",
					name = "voyage_id"
			)
	)
	@JoinColumn(name = "voyage_id")
	@JsonIgnore
	private Voyage currentVoyage;

	public Sailor(String firstName, String lastName, LocalDate birthDate, String contactNo, String address, String email, Rank rank, Gender gender, LocalDate readinessDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
		this.address = address;
		this.contactNo = contactNo;
		this.rank = rank;
		this.gender = gender;
		this.readinessDate = readinessDate;
		this.certificates = new HashSet<>();
		this.contracts = new HashSet<>();
		this.currentVoyage = null;
	}

	@PrePersist
	public void prePersist() {
		calculateAge();
	}

	private void calculateAge() {
		ZoneId zoneId = ZoneId.of("Europe/Bucharest");
		this.age = Period.between(birthDate, LocalDate.now(zoneId)).getYears();
	}

	public void addNewCertificate(Certificate certificate) {
		certificates.add(certificate);
	}

	public void addNewContract(Contract contract) {
		contracts.add(contract);
	}
}

