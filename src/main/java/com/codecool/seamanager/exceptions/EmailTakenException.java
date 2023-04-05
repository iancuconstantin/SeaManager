package com.codecool.seamanager.exceptions;

public class EmailTakenException extends IllegalArgumentException{
	public EmailTakenException(String message){
		super(message);
	}
}
