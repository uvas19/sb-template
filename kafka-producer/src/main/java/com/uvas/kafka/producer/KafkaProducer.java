package com.uvas.kafka.producer;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uvas.pojos.customer.Customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaProducer {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private KafkaProducerProperties kafkaProducerProperties;

	public void sendMessage(Customer customer) {

		String key = "1";
		String value;
		try {
			value = objectMapper.writeValueAsString(customer);
			ProducerRecord<String, String> producerRecord = buildProducerRecord(key, value,
					kafkaProducerProperties.getTopicName());

			ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(producerRecord);

			listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
				@Override
				public void onFailure(Throwable ex) {
					handleFailure(key, value, ex);
				}

				@Override
				public void onSuccess(SendResult<String, String> result) {
					handleSuccess(key, value, result);
				}
			});
		} catch (JsonProcessingException e) {
			log.error("json processing error" + e.getMessage());
			e.printStackTrace();
		}
	}

	private ProducerRecord<String, String> buildProducerRecord(String key, String value, String topic) {
		List<Header> recordHeaders = new ArrayList<>();
//		recordHeaders.add(new RecordHeader("x-biz-svc", "MandateInitiationReqToCsp".getBytes()));
		recordHeaders.add(new RecordHeader("x-biz-svc", "MandateInitiationConfToBsp".getBytes()));
		
		return new ProducerRecord<>(topic, null, key, value, recordHeaders);
	}

	private void handleFailure(String key, String value, Throwable ex) {
		log.error("Error Sending the Message and the exception is {}", ex.getMessage());
		try {
			throw ex;
		} catch (Throwable throwable) {
			log.error("Error in OnFailure: {}", throwable.getMessage());
		}

	}

	private void handleSuccess(String key, String value, SendResult<String, String> result) {
		log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value,
				result.getRecordMetadata().partition());
	}
}
