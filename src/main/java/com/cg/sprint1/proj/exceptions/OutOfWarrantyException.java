package com.cg.sprint1.proj.exceptions;

public class OutOfWarrantyException extends Exception {
	String message;

	public OutOfWarrantyException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
