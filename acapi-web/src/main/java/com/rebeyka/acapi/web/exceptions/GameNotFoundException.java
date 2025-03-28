package com.rebeyka.acapi.web.exceptions;

public class GameNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5410026833836993863L;

	public GameNotFoundException(String message) {
		super(message);
	}
	
	public GameNotFoundException(String message, Throwable t) {
		super(message, t);
	}
}
