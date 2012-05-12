package com.examples.persistence.exception;

public class JpaPersistenceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7749980358519193711L;
	
	public JpaPersistenceException(String message) {
		super(message);
	}

	public JpaPersistenceException(String message, Exception e) {
		super(message, e);
	}

}
