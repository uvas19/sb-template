package com.uvas.entities.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "ADDR_LINE_1")
	private String addressline1;
	
	@Column(name = "ADDR_LINE_2")
	private String addressline2;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "ZIP")
	private String zip;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private Date createdAt;
	
	@CreationTimestamp
	@Column(name = "UPDATED_AT")
	private Date updatedAt;
	
}

