package io.github.erickalandev.rest.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.erickalandev.exception.PedidoNotFoundException;
import io.github.erickalandev.exception.RegraNegocioException;
import io.github.erickalandev.rest.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvance {

	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiErrors handlerRegraNegocioException(RegraNegocioException ex) {
		String messageError = ex.getMessage();
		return new ApiErrors(messageError);
	}

	@ExceptionHandler(PedidoNotFoundException.class)
	@ResponseStatus(NOT_FOUND)
	public ApiErrors handlerPedidoNotFoundException(PedidoNotFoundException ex) {
		return new ApiErrors(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiErrors handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		return new ApiErrors(ex.getBindingResult().getAllErrors().stream().map(error -> {
			return error.getDefaultMessage();
		}).collect(Collectors.toList()));
	}
}