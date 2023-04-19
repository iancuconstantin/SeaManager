package com.codecool.seamanager.exceptions.vessel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VesselNotFoundException extends RuntimeException {
	public VesselNotFoundException(String message) {
		super(message);
	}
}