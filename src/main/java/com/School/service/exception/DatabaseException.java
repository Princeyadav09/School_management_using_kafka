package com.School.service.exception;

import lombok.Getter;

@Getter
public class DatabaseException extends RuntimeException{
    private final String queryError;

    public DatabaseException(String queryError) {
        this.queryError = queryError;
    }
}
