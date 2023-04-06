package com.codecool.seamanager.exceptions.email;

public class EmailTakenException extends IllegalArgumentException{
	public EmailTakenException(String message){
		super(message);
	}
}
