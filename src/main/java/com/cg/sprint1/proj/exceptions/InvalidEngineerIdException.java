package com.cg.sprint1.proj.exceptions;

public class InvalidEngineerIdException extends Exception {
	String message;

	public InvalidEngineerIdException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
