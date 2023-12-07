package com.School.service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface StaffService {

    ResponseEntity<Object> staffCreation();

    ResponseEntity<Object> checkIn(Long staffId);

    ResponseEntity<Object> checkOut(Long staffId);
}
