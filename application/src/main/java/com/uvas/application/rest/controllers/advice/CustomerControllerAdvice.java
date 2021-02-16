package com.uvas.application.rest.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.uvas.pojos.customer.error.StandardError;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomerControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> handleRequestBody(MethodArgumentNotValidException ex){
		log.info("customer controller advice");
		
		return new ResponseEntity<>(new StandardError("CUST-0001", "Missing Required field"), HttpStatus.BAD_REQUEST );
	}
}
