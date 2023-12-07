package com.School.service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface StudentService {

    ResponseEntity<Object> createStudent();

    ResponseEntity<Object> checkIn(Long studentId);

    ResponseEntity<Object> checkOut(Long studentId);

}
