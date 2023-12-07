package com.School.service.controller;

import com.School.service.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/staff")
public class StaffController {

    @Autowired
    StaffService staffService;

    @PostMapping("/creation")
    public ResponseEntity<Object> createStaff(){
        return staffService.staffCreation();
    }

    @PostMapping("/checkin")
    public ResponseEntity<Object> checkIn(@RequestParam(name = "staffId", required = true) Long staffId){
        return staffService.checkIn(staffId);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Object> checkout(@RequestParam(name = "staffId", required = true)Long staffId){
        return staffService.checkOut(staffId);
    }

}
