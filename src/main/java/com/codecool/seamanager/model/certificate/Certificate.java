package com.codecool.seamanager.model.certificate;

import com.codecool.seamanager.model.employee.Employee;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table
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
	@ManyToOne
	@JoinColumn(
			name = "employee_id",
			referencedColumnName = "employee_id",
			nullable = false,
			columnDefinition = "BIGINT"
	)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Employee a_owner;
	@Column(
			name = "description",
			nullable = false,
			columnDefinition = "TEXT"
	)
	private String b_description;
	@Column(
			name = "serial_number",
			nullable = false,
			columnDefinition = "TEXT"
	)
	private String c_serialNumber;
	@Column(
			name = "issue_date",
			nullable = false,
			columnDefinition = "TEXT"
	)
	private String d_issueDate;
	@Column(
			name = "expiry_date",
			columnDefinition = "TEXT"
	)
	private String e_expiryDate;

	public Certificate() {
	}

	public Certificate(Employee owner, String description, String serialNumber, String issueDate, String expiryDate) {
		this.a_owner = owner;
		this.b_description = description;
		this.c_serialNumber = serialNumber;
		this.d_issueDate = issueDate;
		this.e_expiryDate = expiryDate;
		owner.addNewCertificate(this);
	}

	public Long getCertificateId() {
		return certificateId;
	}

	public Employee getOwner() {
		return a_owner;
	}

	public String getDescription() {
		return b_description;
	}

	public String getIssueDate() {
		return d_issueDate;
	}

	public void setD_issueDate(String issueDate) {
		this.d_issueDate = issueDate;
	}

	public String getExpiryDate() {
		return e_expiryDate;
	}

	public void setE_expiryDate(String expiryDate) {
		this.e_expiryDate = expiryDate;
	}

	public String getSerialNumber() {
		return c_serialNumber;
	}

	public void setA_owner(Employee owner) {
		this.a_owner = owner;
	}

	public void setB_description(String description) {
		this.b_description = description;
	}

	public void setC_serialNumber(String serialNumber) {
		this.c_serialNumber = serialNumber;
	}

	@Override
	public String toString() {
		return "Certificate{" +
				"id=" + certificateId +
				", ownerId=" + a_owner +
				", description='" + b_description + '\'' +
				", serialNumber='" + c_serialNumber + '\'' +
				", issueDate='" + d_issueDate + '\'' +
				", expiryDate='" + e_expiryDate + '\'' +
				'}';
	}
}
