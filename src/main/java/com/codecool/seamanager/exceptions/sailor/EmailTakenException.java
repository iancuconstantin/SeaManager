package com.codecool.seamanager.exceptions.sailor;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EmailTakenException extends DuplicateKeyException {
	public EmailTakenException(String message){
		super(message);
	}
}
