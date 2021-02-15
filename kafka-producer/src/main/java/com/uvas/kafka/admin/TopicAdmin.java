package com.uvas.kafka.admin;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

import com.uvas.kafka.producer.KafkaProducerProperties;

@Configuration
@Profile("local")
public class TopicAdmin {

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;
	
    @Bean
    public NewTopic libraryEvents(){
        return TopicBuilder.name(kafkaProducerProperties.getTopicName())
                .partitions(1)
                .replicas(1)
                .build();
    }

}
