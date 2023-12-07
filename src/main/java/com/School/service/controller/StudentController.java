package com.School.service.controller;

import com.School.service.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/creation")
    public ResponseEntity<Object> createStudent(){
        return studentService.createStudent();
    }

    @PostMapping("/checkin")
    public ResponseEntity<Object> checkIn(@RequestParam(name = "studentId", required = true) Long studentId){
            return studentService.checkIn(studentId);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Object> checkOut(@RequestParam(name = "studentId", required = true) Long studentId){
        return studentService.checkOut(studentId);
    }

}
