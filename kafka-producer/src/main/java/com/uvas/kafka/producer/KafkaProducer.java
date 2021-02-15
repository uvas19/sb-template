package com.uvas.kafka.producer;

import org.springframework.stereotype.Component;

import com.uvas.pojos.customer.Customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaProducer {

	public void sendMessage(Customer customer) {
		log.info("KafkaProducer -> send message");
		
	}

}
