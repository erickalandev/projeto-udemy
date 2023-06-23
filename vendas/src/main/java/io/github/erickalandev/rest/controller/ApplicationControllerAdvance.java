package io.github.erickalandev.rest.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.erickalandev.exception.RegraNegocioException;
import io.github.erickalandev.rest.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvance {
	
	@ExceptionHandler(RegraNegocioException.class)
	public ApiErrors handlerRegraNegocioException(RegraNegocioException ex) {
		String messageError = ex.getMessage();
		return new ApiErrors(messageError);
	}
}