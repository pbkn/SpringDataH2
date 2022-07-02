package com.example.springdatah2.exception;

import java.util.Date;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order
public class ControllerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
		return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleGlobalException(Exception ex, WebRequest request) {
		return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(),
				request.getDescription(false));
	}
}
