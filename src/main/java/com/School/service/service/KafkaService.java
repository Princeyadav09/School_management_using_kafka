package com.School.service.service;

import org.springframework.stereotype.Component;

@Component
public interface KafkaService {

       void sendToConsumer(Object message);
}
