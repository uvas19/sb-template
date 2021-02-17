package com.uvas.kafka.consumer.interceptor;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class KafkaConsumerInterceptor implements ConsumerInterceptor<String, String>{

	@Override
	public void configure(Map<String, ?> configs) {
		
	}


	@Override
	public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
		log.info("on consume records");
		return records;
	}


	@Override
	public void close() {

		
	}


	@Override
	public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
		log.info("on commit offsets");
		
	}
}
