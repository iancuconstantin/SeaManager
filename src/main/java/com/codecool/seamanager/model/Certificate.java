package com.codecool.seamanager.model;

import java.util.Objects;

public class Certificate {
	private final Employee owner; //TODO - keep field??
	private final String description;
	private final int serialNumber;
	private String issueDate;
	private String expiryDate;

	public Certificate(Employee owner, String description, int serialNumber, String issueDate, String expiryDate) {
		this.owner = owner;
		this.description = description;
		this.serialNumber = serialNumber;
		this.issueDate = issueDate;
		this.expiryDate = expiryDate;
	}

	public Employee getOwner() {
		return owner;
	}

	public String getDescription() {
		return description;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getSerialNumber() {
		return serialNumber;
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
