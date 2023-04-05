package com.codecool.seamanager.model;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "certificate")
public class Certificate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee owner;
	@NotNull
	@Column(name = "description")
	private String description;
	@NotNull
	@Column(name = "serialNumber")
	private int serialNumber;
	@NotNull
	@Column(name = "issueDate")
	private Date issueDate;
	@NotNull
	@Column(name = "expiryDate")
	private Date expiryDate;

	public Long getId() {
		return id;
	}

	public Employee getOwner() {
		return owner;
	}

	public String getDescription() {
		return description;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOwner(Employee owner) {
		this.owner = owner;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Certificate that = (Certificate) o;
		return serialNumber == that.serialNumber && owner.equals(that.owner) && description.equals(that.description) && issueDate.equals(that.issueDate) && Objects.equals(expiryDate, that.expiryDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(owner, description, serialNumber, issueDate, expiryDate);
	}

	@Override
	public String toString() {
		return "Certificate{" +
				"owner=" + owner +
				", description='" + description + '\'' +
				", serialNumber=" + serialNumber +
				", issueDate='" + issueDate + '\'' +
				", expiryDate='" + expiryDate + '\'' +
				'}';
	}
}
