package com.uvas.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ConsumerAwareErrorHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumerErrorHandler implements ConsumerAwareErrorHandler {

	@Override
	public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
		log.error("thrownException -> {} , for data -> {} , for consumer group id -> {}", thrownException.getMessage(),
				data.value(), consumer.groupMetadata().groupId());
		
	}

}
