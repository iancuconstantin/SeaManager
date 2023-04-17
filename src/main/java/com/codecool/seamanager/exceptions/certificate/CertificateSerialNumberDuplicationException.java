package com.codecool.seamanager.exceptions.certificate;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CertificateSerialNumberDuplicationException extends DuplicateKeyException {
	public CertificateSerialNumberDuplicationException(String message) {
		super(message);
	}
}
