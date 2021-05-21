package com.cg.sprint1.proj.exceptions;

public class InValidComplaintIdException extends Exception {
	String message;
	public InValidComplaintIdException (String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
