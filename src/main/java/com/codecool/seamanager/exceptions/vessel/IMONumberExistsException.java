package com.codecool.seamanager.exceptions.vessel;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class IMONumberExistsException extends DuplicateKeyException {
	public IMONumberExistsException(String msg) {
		super(msg);
	}
}
