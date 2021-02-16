package com.uvas.application.rest.controllers;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uvas.pojos.customer.Customer;
import com.uvas.services.customer.CustomerService;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
    ObjectMapper objectMapper = new ObjectMapper();
    
    @MockBean
    CustomerService service;
    
	@Test
	void createCustomer() throws Exception {
		//given		
		Customer customer = new Customer();
		customer.setFirstName("test-name-fn");
		customer.setLastName("test-name-ln");
		
		//when
        String json = objectMapper.writeValueAsString(customer);
//        when(service.create(customer));
        
		mockMvc.perform(post("/customers")
			.content(json)
        	.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		//then
	
	
	}
}
