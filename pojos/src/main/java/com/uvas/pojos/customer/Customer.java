package com.uvas.pojos.customer;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(Include.NON_NULL)
public class Customer {

	private long id;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	private String addressline1;
	private String addressline2;
	private String city;
	private String state;
	private String zip;
	private String country;
}
