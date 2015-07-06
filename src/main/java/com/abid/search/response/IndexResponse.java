package com.abid.search.response;

public class IndexResponse extends BaseResponse {
	private boolean isSuccess;
	private String message;

	public IndexResponse(boolean isSuccess, String message) {
		this.isSuccess = isSuccess;
		this.message = message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
