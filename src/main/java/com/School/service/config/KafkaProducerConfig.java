package com.School.service.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${bootstrap.server.url}")
    private String bootstrapServerUrl;

    /**
     * Kafka templates.
     *
     * @return the kafka template
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplates() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Producer factory.
     *
     * @return the producer factory
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = this.createProducerConfig();
        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Create producer config
     *
     * @return Map
     */
    private Map<String, Object> createProducerConfig() {
        final Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServerUrl);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }
}
