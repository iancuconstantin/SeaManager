package com.codecool.seamanager.exceptions.contract;

import org.springframework.dao.DuplicateKeyException;

public class ContractExistsException extends DuplicateKeyException {
	public ContractExistsException(String message) {
		super(message);
	}
}
