package com.cg.sprint1.proj.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cg.sprint1.proj.exceptions.*;

@ControllerAdvice
public class GlobalExceptionHandlingAdvice {
	@ExceptionHandler(InvalidClientIdException.class)
	public ResponseEntity<String> exceptionhandling(InvalidClientIdException ex) {
		System.out.println(ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InValidComplaintIdException.class)
	public ResponseEntity<String> exceptionhandling(InValidComplaintIdException ex) {
		System.out.println(ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InValidDomainException.class)
	public ResponseEntity<String> exceptionhandling(InValidDomainException ex) {
		System.out.println(ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidEngineerIdException.class)
	public ResponseEntity<String> exceptionhandling(InvalidEngineerIdException ex) {
		System.out.println(ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InValidModelNumberException.class)
	public ResponseEntity<String> exceptionhandling(InValidModelNumberException ex) {
		System.out.println(ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OutOfWarrantyException.class)
	public ResponseEntity<String> exceptionhandling(OutOfWarrantyException ex) {
		System.out.println(ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PermissionDeniedException.class)
	public ResponseEntity<String> exceptionhandling(PermissionDeniedException ex) {
		System.out.println(ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

}
