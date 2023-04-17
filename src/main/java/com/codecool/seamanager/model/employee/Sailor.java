package com.codecool.seamanager.model.employee;

import com.codecool.seamanager.constraints.BirthDate;
import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.vessel.Vessel;
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
@ToString
public class Sailor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(
			name = "employee_id",
			updatable = false
	)
	private Long employeeId;
	@NotBlank
	@Size(
			min = 2,
			max = 25,
			message = "First name should be min 3 and max 25 characters long"
	)
	@Column(
			columnDefinition = "TEXT"
	)
	private String firstName;
	@NotBlank
	@Size(
			min = 2,
			max = 15,
			message = "Last name should be min 2 and max 15 characters long"
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
	@JsonIgnore
	@OneToMany(
			targetEntity = Certificate.class,
			mappedBy = "owner"
	)
	private Set<Certificate> certificates;

	@ManyToOne
	@JoinColumn(
			referencedColumnName = "id",
			columnDefinition = "BIGINT",
			foreignKey = @ForeignKey(name = "vessel_id_foreign")
	)
	private Vessel vessel;

	public Sailor(String firstName, String lastName, LocalDate birthDate, String contactNo, String address, String email, Rank rank, Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
		this.address = address;
		this.contactNo = contactNo;
		this.rank = rank;
		this.gender = gender;
		this.certificates = new HashSet<>();
		this.vessel = null;
	}

	@PrePersist
	public void prePersist(){
		calculateAge();
	}

	private void calculateAge() {
		this.age = Period.between(birthDate, LocalDate.now()).getYears();
	}

	public void addNewCertificate(Certificate certificate) {
		certificates.add(certificate);
	}
}

