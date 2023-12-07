package com.School.service.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    private ResponseUtils() {

    }
    public static ResponseEntity<Object> getResponseEntity(Object responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<Object>(responseMessage, httpStatus);
    }
}
