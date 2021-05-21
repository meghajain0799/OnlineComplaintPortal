package com.cg.sprint1.proj.exceptions;

public class InvalidPasswordException extends Exception {
	String message;

	public InvalidPasswordException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
