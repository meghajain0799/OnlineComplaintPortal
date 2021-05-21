package com.cg.sprint1.proj.exceptions;

public class PermissionDeniedException extends Exception {
	String message;

	public PermissionDeniedException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
