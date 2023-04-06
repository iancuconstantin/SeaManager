package com.codecool.seamanager.exceptions.email;

public class EmployeeNotFoundException extends IllegalStateException {
	public EmployeeNotFoundException(String message) {
		super(message);
	}
}
