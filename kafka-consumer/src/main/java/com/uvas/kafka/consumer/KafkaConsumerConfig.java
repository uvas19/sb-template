package com.uvas.kafka.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.uvas.kafka.consumer.interceptor.KafkaConsumerInterceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableKafka
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerConfig {

	@Autowired
	private final KafkaProperties kafkaProperties;

	/**
	 * 
	 * setting up the kafka listenner container properties
	 * 
	 * @return
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
//		factory.getContainerProperties().setAckMode(AckMode.MANUAL);
		factory.setConcurrency(1);
		factory.setRetryTemplate(retryTemplate());
		factory.setRecoveryCallback(callback());
		factory.setErrorHandler(new KafkaConsumerErrorHandler());
//		factory.setErrorHandler(new SeekToCurrentErrorHandler(new FixedBackOff(1000L, 2L)));

		return factory;
	}
	
	/**
	 * 
	 * setting up the default kafka consumer factory with custom properties as needed by application
	 * 
	 * @return
	 */
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.FALSE);
		props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, KafkaConsumerInterceptor.class.getName());
		return new DefaultKafkaConsumerFactory<>(props);
	}


	private void handleRecovery(ConsumerRecord<String, String> consumerRecord) {
		log.info("TODO - handle recovery - could publish to topic again for retryable exceptions");

	}

	/**
	 * 
	 * retry template with retry and back off policy
	 * 
	 * @return
	 */
	@Bean
	public RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setRetryPolicy(retryPolicy());
		retryTemplate.setBackOffPolicy(backOffPolicy());
		return retryTemplate;
	}
	/**
	 * back off policy for retries
	 * 
	 * @return
	 */
	@Bean
	public BackOffPolicy backOffPolicy() {
		FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
		fixedBackOffPolicy.setBackOffPeriod(1000);
		return fixedBackOffPolicy;
	}
	/**
	 * add here the exceptions which should or should not be tried
	 * by setting the boolean as true or false respectively
	 * for e.g - JsonProcessingException may not needed to be retried
	 * while RecoverableDataAccessException could be retried
	 * 
	 * @return
	 */
	
	private RetryPolicy retryPolicy() {

		Map<Class<? extends Throwable>, Boolean> exceptions = new HashMap<>();
		
		// Don't re-try in case of JsonProcessingException
		exceptions.put(JsonProcessingException.class, false);

		// try retry in case of RecoverableDataAccessException
		exceptions.put(RecoverableDataAccessException.class, true);

		return new SimpleRetryPolicy(3, exceptions, true);
	}

	/**
	 * recovery callback for the exceptions that could be recovered but have exhausted the retry policy
	 * 
	 * @return
	 */
	private RecoveryCallback<? extends Object> callback() {
		return context -> {
			if (context.getLastThrowable().getCause() instanceof RecoverableDataAccessException) {
				log.info("recover from exception");
				ConsumerRecord<String, String> consumerRecord = (ConsumerRecord<String, String>) context
						.getAttribute("record");
				handleRecovery(consumerRecord);
			} else {
				log.info("cannot recover from exception");
				throw new RuntimeException(context.getLastThrowable().getCause());
			}
			return null;
		};
	}
}
