package com.bridgeit.tradefinance.Exception;

public class DuplicateEmailException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DuplicateEmailException(String message) {
		
		super(message);
		
	}

}
