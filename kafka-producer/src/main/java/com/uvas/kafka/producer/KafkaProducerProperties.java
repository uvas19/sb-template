package com.uvas.kafka.producer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "kafka")
@Configuration
@Data
public class KafkaProducerProperties {

	private String topicName;
}
