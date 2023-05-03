package com.codecool.seamanager.model.certificate;

import com.codecool.seamanager.model.employee.Sailor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(name = "serialNumber_uk", columnNames = {"serial_number"})
		}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Certificate {
	@Id
	@GeneratedValue(
			strategy = GenerationType.AUTO
	)
	@Column(
			name = "certificate_id",
			updatable = false
	)
	private Long certificateId;
	@NotNull(
			message = "Certificate should have an owner."
	)
	@ManyToOne
	@JoinColumn(
			name = "employee_id",
			referencedColumnName = "employee_id",
			columnDefinition = "BIGINT",
			foreignKey = @ForeignKey(name = "owner_id_foreign")
	)
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Sailor owner;
	@NotNull(
			message = "Certificate type cannot be empty."
	)
	@Column(
			name = "description",
			columnDefinition = "TEXT"
	)
	@Enumerated(EnumType.STRING)
	private CertificateType type;
	@NotBlank(message = "Serial number cannot be empty.")
	@Column(
			name = "serial_number",
			columnDefinition = "TEXT"
	)
	private String serialNumber;
	@NotNull(message = "Issue date cannot be empty")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "Issue date cannot be in the future.")
	@Column(
			name = "issue_date",
			columnDefinition = "DATE"
	)
	@JsonFormat(
			shape = STRING,
			pattern = "yyyy-MM-dd"
	)
	private LocalDate issueDate;
	@Column(
			name = "expiry_date",
			columnDefinition = "DATE"
	)
	@JsonFormat(
			shape = STRING,
			pattern = "yyyy-MM-dd"
	)
	private LocalDate expiryDate;

	public Certificate(Sailor owner, CertificateType description, String serialNumber, LocalDate issueDate, LocalDate expiryDate) {
		this.owner = owner;
		this.type = description;
		this.serialNumber = serialNumber;
		this.issueDate = issueDate;
		this.expiryDate = expiryDate;
		owner.addNewCertificate(this);
	}

	@Override
	public String toString() {
		return "Certificate{" +
				"id=" + certificateId +
				", ownerId=" + owner +
				", description='" + type + '\'' +
				", serialNumber='" + serialNumber + '\'' +
				", issueDate='" + issueDate + '\'' +
				", expiryDate='" + expiryDate + '\'' +
				'}';
	}
}
