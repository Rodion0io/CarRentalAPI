package com.example.exception.handler;

import com.example.exception.ApiError;
import com.example.exception.CustomException;
import com.example.exception.ExceptionType;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;


@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ApiError> handle(CustomException error){
        log.error("Custom exception: {}", error.getMessage());
        ApiError apiError = new ApiError(getStatus(error.getType()), error.getMessage());
        return new ResponseEntity<>(apiError, getStatus(error.getType()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiError> handle(Exception error){
        log.error("Exception: {}", error.getMessage());
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({JwtException.class})
    public ResponseEntity<ApiError> handle(JwtException error){
        log.error("Custom exception: {}", error.getMessage());
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Invalid token");
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ApiError> handle(AuthenticationException error){
        log.error("Custom exception: {}", error.getMessage());
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, error.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ServletException.class})
    public ResponseEntity<ApiError> handle(ServletException e) {
        log.error("ServletException occurred: {}", e.getMessage());
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiError> handle(AccessDeniedException e) {
        log.error("AccessDeniedException occurred: {}", e.getMessage());
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }


    private HttpStatus getStatus(ExceptionType type) {
        return switch (type) {
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case BAD_REQUEST, ILLEGAL -> HttpStatus.BAD_REQUEST;
            case ALREADY_EXIST -> HttpStatus.CONFLICT;
            case FATAL -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
