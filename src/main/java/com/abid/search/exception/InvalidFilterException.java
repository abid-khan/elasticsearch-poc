package com.abid.search.exception;

public class InvalidFilterException extends RuntimeException {
	private String message;

	public InvalidFilterException(String message) {
		super();
		this.message = message;
	}

	public InvalidFilterException(String message, Throwable cause) {
		super(cause);
		this.message = message;
	}

	@Override public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
