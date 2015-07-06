package com.abid.search.exception;

public class SearchServiceException extends RuntimeException {
	private String message;

	public SearchServiceException(Throwable cause) {
		super(cause);
	}

	public SearchServiceException(String message, Throwable cause) {
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
