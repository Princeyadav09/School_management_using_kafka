package com.School.service.service.implementation;

import com.School.service.config.KafkaProducerConfig;
import com.School.service.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    KafkaProducerConfig kafkaProducerConfig;

    @Override
    public void sendToConsumer(Object message){
       kafkaProducerConfig.kafkaTemplates().send("school",message);
       return;
    }
}
