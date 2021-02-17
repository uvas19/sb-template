package com.uvas.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uvas.pojos.customer.Customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ConditionalOnProperty(name = "kafka.consumer.enabled", havingValue = "true")
@RequiredArgsConstructor
public class KafkaConsumer {

	@Autowired
	private final ObjectMapper objectMapper;

	@KafkaListener(topics = { "${kafka.topic-name}" }, groupId = "${kafka.consumer.group-id}")
	public void onMessage(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
		log.info("kafka consumer on message : {} ", consumerRecord);

		Customer customer = objectMapper.readValue(consumerRecord.value(), Customer.class);
		log.info("customer :: {}", customer.toString());
		// PROCESS REQUEST
		// acknowledgment.acknowledge();
	}
}
