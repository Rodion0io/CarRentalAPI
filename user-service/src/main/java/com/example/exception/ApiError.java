package com.example.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ApiError {
    private final HttpStatus statusCode;
    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final String time;

    public ApiError(HttpStatus statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
        time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"));
    }
}
