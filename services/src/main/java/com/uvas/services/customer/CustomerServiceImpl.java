package com.uvas.services.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uvas.common.exceptions.ResourceNotFoundException;
import com.uvas.entities.customer.CustomerEntity;
import com.uvas.kafka.producer.KafkaProducer;
import com.uvas.pojos.customer.Customer;
import com.uvas.repository.customer.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private static final String MESSAGE = "Customer not found";
	
	private final CustomerRepository customerRepository;
	private final KafkaProducer kafkaProducer;

	@Override
	public Customer getOne(Long id) {
		log.info("getOne service");
		Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
		if(customerEntity.isPresent()) {
			CustomerEntity entity = customerEntity.get();
			return getCustomerDTOFromEntity(entity);
		}else {
			throw new ResourceNotFoundException(MESSAGE);
		}
	}

	private Customer getCustomerDTOFromEntity(CustomerEntity entity) {
		 Customer customer = new Customer();
		 customer.setId(entity.getSid());
		 customer.setFirstName(entity.getFirstName());
		 customer.setLastName(entity.getLastName());
		 customer.setAddressline1(entity.getAddressline1());
		 customer.setAddressline2(entity.getAddressline2());
		 customer.setCity(entity.getCity());
		 customer.setState(entity.getState());
		 customer.setZip(entity.getZip());
		 customer.setCountry(entity.getCountry());
		 customer.setCustomerType(entity.getCustomerType());
		 customer.setMsgCreateDateTime(entity.getMsgCreateDateTime());
		 customer.setCustomerType(entity.getCustomerType());
		 return customer;
	}
	
	private CustomerEntity getCustomerEntityFromDTO(Customer dto) {
		CustomerEntity entity = new CustomerEntity();
		setCustomerEntity(dto, entity);
		return entity;	
	}


	private void setCustomerEntity(Customer dto, CustomerEntity entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setAddressline1(dto.getAddressline1());
		entity.setAddressline2(dto.getAddressline2());
		entity.setCity(dto.getCity());
		entity.setState(dto.getState());
		entity.setZip(dto.getZip());
		entity.setCountry(dto.getCountry());
		entity.setMsgCreateDateTime(dto.getMsgCreateDateTime());
		entity.setCustomerType(dto.getCustomerType());
	}
	
	
	@Override
	public List<Customer> getAll() {
		log.info("getAll service");
		List<CustomerEntity> list = customerRepository.findAll();
		List<Customer> customers = new ArrayList<>();
		for(CustomerEntity entity : list) {
			customers.add(getCustomerDTOFromEntity(entity));
		}
		return customers;
	}

	@Override
	public Customer create(Customer customer) {
		log.info("create service");
		
		CustomerEntity entity = customerRepository.save(getCustomerEntityFromDTO(customer));
		kafkaProducer.sendMessage(getCustomerDTOFromEntity(entity));
		
		return getCustomerDTOFromEntity(entity);
	}

	@Override
	public Customer update(Long id, Customer customer) {
		log.info("update service");
		Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
		if(customerEntity.isPresent()) {
			CustomerEntity entity = customerEntity.get();
			setCustomerEntity(customer, entity);
			kafkaProducer.sendMessage(getCustomerDTOFromEntity(entity));
			customerRepository.save(entity);
		}else {
			throw new ResourceNotFoundException(MESSAGE);
		}
		return customer;
	}

	@Override
	public void delete(Long id) {
		log.info("delete service");
		Optional<CustomerEntity> entity = customerRepository.findById(id);
		if(entity.isPresent()) {
			customerRepository.delete(entity.get());
			kafkaProducer.sendMessage(getCustomerDTOFromEntity(entity.get()));
		}else {
			throw new ResourceNotFoundException(MESSAGE);
		}
		
	}

}

