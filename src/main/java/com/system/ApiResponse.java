package com.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {
	private HttpStatus status;
	private Object data;
	private String message;

	public ApiResponse() {
		this(null);
	}

	public ApiResponse(Object data) {
		this.data = data;
		this.message = null;
	}

	public ResponseEntity<ApiResponse> send(HttpStatus status) {
		this.status = status;
		return new ResponseEntity<ApiResponse>(this, status);
	}

	public ResponseEntity<ApiResponse> send(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
		return new ResponseEntity<ApiResponse>(this, status);
	}

	public ResponseEntity<ApiResponse> sendData(Object data, HttpStatus status, String message) {
		this.status = status;
		this.message = message;
		this.data = data;
		return new ResponseEntity<ApiResponse>(this, status);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return this.status;
	}
}
