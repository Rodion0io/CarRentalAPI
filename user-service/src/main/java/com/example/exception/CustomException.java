package com.example.exception;

import lombok.Getter;


@Getter
public class CustomException extends RuntimeException {
    private final ExceptionType type;

    public CustomException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}