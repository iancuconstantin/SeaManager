package com.codecool.seamanager.model;

import java.util.Objects;

public class Certificate {
	private final String ownerID;
	private final String description;
	private final int serialNumber;
	private String issueDate;
	private String expiryDate;

	public Certificate(String ownerID, String description, int serialNumber, String issueDate, String expiryDate) {
		this.ownerID = ownerID;
		this.description = description;
		this.serialNumber = serialNumber;
		this.issueDate = issueDate;
		this.expiryDate = expiryDate;
	}

	public String getOwnerID() {
		return ownerID;
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
		return serialNumber == that.serialNumber && ownerID.equals(that.ownerID) && description.equals(that.description) && issueDate.equals(that.issueDate) && Objects.equals(expiryDate, that.expiryDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ownerID, description, serialNumber, issueDate, expiryDate);
	}

	@Override
	public String toString() {
		return "Certificate{" +
				"ownerID=" + ownerID +
				", description='" + description + '\'' +
				", serialNumber=" + serialNumber +
				", issueDate='" + issueDate + '\'' +
				", expiryDate='" + expiryDate + '\'' +
				'}';
	}
}
