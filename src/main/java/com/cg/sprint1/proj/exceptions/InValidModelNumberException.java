package com.cg.sprint1.proj.exceptions;

public class InValidModelNumberException extends Exception {
	String message;

	public InValidModelNumberException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
