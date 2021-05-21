package com.cg.sprint1.proj.exceptions;

public class InvalidClientIdException extends Exception {

	String message;
	public InvalidClientIdException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
