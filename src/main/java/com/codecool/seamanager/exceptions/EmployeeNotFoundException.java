package com.codecool.seamanager.exceptions;

public class EmployeeNotFoundException extends IllegalStateException {
	public EmployeeNotFoundException(String message) {
		super(message);
	}
}
