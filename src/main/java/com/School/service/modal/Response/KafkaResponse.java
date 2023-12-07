package com.School.service.modal.Response;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Data
@Component
public class KafkaResponse {

    private String name;
    private String message;
    private ZonedDateTime time;
    private String type;
}
