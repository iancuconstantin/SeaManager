package com.codecool.seamanager.exceptions.certificate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CertificateNotFoundException extends RuntimeException {
	public CertificateNotFoundException(String message) {
		super(message);
	}
}
