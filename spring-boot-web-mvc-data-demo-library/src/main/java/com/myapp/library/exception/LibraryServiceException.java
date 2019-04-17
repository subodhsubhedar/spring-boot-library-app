package com.myapp.library.exception;

/**
 * 
 * @author Admin
 *
 */
public class LibraryServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LibraryServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public LibraryServiceException(String message) {
		super(message);
	}

}
