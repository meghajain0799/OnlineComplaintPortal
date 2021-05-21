package com.cg.sprint1.proj.exceptions;

public class InvalidCredentialsException extends Exception {
	String message;
	public InvalidCredentialsException (String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
