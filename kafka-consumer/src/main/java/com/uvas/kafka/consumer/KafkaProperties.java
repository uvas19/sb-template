package com.uvas.kafka.consumer;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "kafka.consumer")
@Data
public class KafkaProperties {

	private List<String> bootstrapServers;
	private String groupId;
}
