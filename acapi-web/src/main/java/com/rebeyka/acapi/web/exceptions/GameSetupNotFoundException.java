package com.rebeyka.acapi.web.exceptions;

public class GameSetupNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1979915911956254766L;

	public GameSetupNotFoundException(String gameName) {
		super("Could not find Game Setup with Name %s".formatted(gameName));
	}
	
	public GameSetupNotFoundException(String gameName, Throwable throwable) {
		super("Failed to properly create Game Setup with name %s".formatted(gameName), throwable);
	}
}
