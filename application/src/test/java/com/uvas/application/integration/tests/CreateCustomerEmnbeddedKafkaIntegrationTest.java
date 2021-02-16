package com.uvas.application.integration.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import com.uvas.pojos.customer.Customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = { "TEST-TOPIC-001" }, partitions = 3)
@TestPropertySource(properties = { "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
		"spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}" })
public class CreateCustomerEmnbeddedKafkaIntegrationTest {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	EmbeddedKafkaBroker embeddedKafkaBroker;

	private Consumer<String, String> consumer;

	@BeforeEach
	void setUp() {
		Map<String, Object> configs = new HashMap<>(
				KafkaTestUtils.consumerProps("group1", "true", embeddedKafkaBroker));
		consumer = new DefaultKafkaConsumerFactory<String, String>(configs, new StringDeserializer(),
				new StringDeserializer()).createConsumer();
		embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
	}

	@Test
	@Timeout(5)
	void createCustomer() throws InterruptedException {
		// given
		Customer customer = new Customer();
		customer.setFirstName("test-name-fn");
		customer.setLastName("test-name-ln");

		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", MediaType.APPLICATION_JSON.toString());

		HttpEntity<Customer> request = new HttpEntity<>(customer, headers);

		// when
		ResponseEntity<Customer> response = restTemplate.exchange("/customers", HttpMethod.POST, request,
				Customer.class);

		// then
		assertEquals(HttpStatus.OK, response.getStatusCode());

		ConsumerRecord<String, String> consumerRecord = KafkaTestUtils.getSingleRecord(consumer, "TEST-TOPIC-001");

		String key = consumerRecord.key();
		String value = consumerRecord.value();

		log.info("createCustomerkey -> " + key);
		log.info("createCustomervalue -> " + value);

	}
}