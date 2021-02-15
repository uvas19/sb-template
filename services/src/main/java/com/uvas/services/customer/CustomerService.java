package com.uvas.services.customer;

import java.util.List;

import com.uvas.pojos.customer.Customer;

public interface CustomerService {

	public Customer getOne(Long id);
	public List<Customer> getAll();
	public Customer create(Customer customer);
	public Customer update(Long id, Customer customer);
	public void delete(Long id);
}
