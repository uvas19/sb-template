package com.uvas.common.exceptions;

public class PastDateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PastDateException(String message) {
		super(message);
	}

	public PastDateException(String message, Throwable t) {
		super(message, t);
	}
}