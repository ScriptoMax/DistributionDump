package com.hackcent.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	
	private int status;
	private String message;
	private long timeStamp;	
	
	public ErrorResponse(String message) {
		this.message = message;
	}
}