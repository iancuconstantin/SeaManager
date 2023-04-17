package com.codecool.seamanager.exceptions.email;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SailorNotFoundException extends RuntimeException {
	public SailorNotFoundException(String message) {
		super(message);
	}
}
