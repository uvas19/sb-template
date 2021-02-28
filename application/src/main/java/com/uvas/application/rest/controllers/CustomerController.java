package com.uvas.application.rest.controllers;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uvas.pojos.customer.Customer;
import com.uvas.services.customer.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomerController {


	private final CustomerService customerService;
	
	
	@PostMapping("/customers")
	public ResponseEntity<Customer> create(/*@Valid*/ @RequestBody Customer customer){
		log.info("create controller");
		log.info("request :: "+ customer.toString());
		return ResponseEntity.ok().body(customerService.create(customer));
	}
	
	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> udpate(@PathVariable Long id, @RequestBody Customer customer){
		log.info("udpate controller");
		return ResponseEntity.ok().body(customerService.update(id, customer));
	}
	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAll(){
		log.info("getAll controller");
		return ResponseEntity.ok().body(customerService.getAll());
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getOne(@PathVariable Long id){
		log.info("getOne controller");
		return ResponseEntity.ok().body(customerService.getOne(id));
	}
	
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		log.info("delete controller");
		customerService.delete(id);		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
	}
	
}

