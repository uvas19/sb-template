package com.uvas.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumer {
	
	@KafkaListener(topics = {"${kafka.topic-name}"})
	public void onMessage(ConsumerRecord<String, String> consumerRecord) {
		log.info("kafka consumer on message : {} ", consumerRecord);
		
		
	}
}
