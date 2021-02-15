package com.uvas.kafka.producer;

import com.uvas.pojos.customer.Customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaProducer {

	public void sendMessage(Customer customer) {
		log.info("KafkaProducer -> send message");
		
	}

}
