package com.uvas.application.integration.tests;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.uvas.pojos.customer.Customer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateCustomerIntegrationTest {

	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	void createCustomer() {
		//given
		Customer customer = new Customer();
		customer.setFirstName("test-name-fn");
		customer.setLastName("test-name-ln");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", MediaType.APPLICATION_JSON.toString());
		
		HttpEntity<Customer> request = new HttpEntity<>(customer, headers);
		
		//when
		ResponseEntity<Customer> response = restTemplate.exchange("/customers", HttpMethod.POST, request, Customer.class);
		
		//then
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
