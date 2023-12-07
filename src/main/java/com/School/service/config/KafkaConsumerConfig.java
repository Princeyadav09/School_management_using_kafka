package com.School.service.config;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;


@Configuration
public class KafkaConsumerConfig {

    public static final Logger Log = LoggerFactory.getLogger(KafkaConfig.class);

    @KafkaListener(topics = "school", groupId = "group")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        Log.info("object value: "+ record.value());
    }

}
