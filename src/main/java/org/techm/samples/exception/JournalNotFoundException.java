package org.techm.samples.exception;

public class JournalNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JournalNotFoundException(String message) {
		super(message);
	}

	
}
