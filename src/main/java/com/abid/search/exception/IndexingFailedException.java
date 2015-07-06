package com.abid.search.exception;

public class IndexingFailedException extends RuntimeException {
	private String message;

	public IndexingFailedException(Throwable cause) {
		super(cause);
	}

	public IndexingFailedException(String message, Throwable cause) {
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
