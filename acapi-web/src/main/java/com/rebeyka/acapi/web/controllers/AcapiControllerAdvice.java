package com.rebeyka.acapi.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rebeyka.acapi.web.exceptions.GameNotFoundException;

@ControllerAdvice
public class AcapiControllerAdvice {

	private static final Logger LOG = LogManager.getLogger();
	
	@ExceptionHandler
	public ErrorResponse handleException(Exception exception) {
		LOG.error("Generic error",exception);
		return ErrorResponse.create(exception, HttpStatus.INTERNAL_SERVER_ERROR, "error: %s".formatted(exception.getMessage()));
	}
	
	@ExceptionHandler
	public ErrorResponse handleNotFound(GameNotFoundException e) {
		LOG.error("Not found",e);
		return ErrorResponse.create(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
}
