package com.example.exception;

public class CustomException extends RuntimeException {
    private ExceptionType type;

    public CustomException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
