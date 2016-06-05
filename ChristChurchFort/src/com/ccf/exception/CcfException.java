package com.ccf.exception;

public class CcfException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5530654608827822552L;
	
	public CcfException(String message){
		this.message = message;
	}
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
