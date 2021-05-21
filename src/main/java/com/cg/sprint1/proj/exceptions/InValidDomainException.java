package com.cg.sprint1.proj.exceptions;

public class InValidDomainException extends Exception {
	String message;
	public  InValidDomainException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
