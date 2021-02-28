package com.uvas.application.rest.controllers.advice;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.uvas.RequestContext;
import com.uvas.pojos.customer.error.StandardError;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomerControllerAdvice {

	@Autowired
	private final RequestContext requestContext;

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> handleRequestBody(ConstraintViolationException ex) {
		log.info("customer controller advice");

		return new ResponseEntity<>(new StandardError("CUST-0001", ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandardError> handleHttpMessageDeserialization(HttpMessageNotReadableException ex) {
		log.info("customer controller advice");

		log.info(ex.getMostSpecificCause().getMessage());
		Object requestBody = requestContext.getRequestBody();
		log.info("requestBody : {}"+requestBody);
		return new ResponseEntity<>(new StandardError("CUST-0002", ex.getMostSpecificCause().getMessage()),
				HttpStatus.BAD_REQUEST);
	}

}
