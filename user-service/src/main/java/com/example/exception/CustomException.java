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

// пример эксепшн хендлера
//@Slf4j
//@ExceptionHandler
//public ResponseEntity<String> handle(Type exception){
//    log.error(...)
//    return new ResponseEntity<>(exception.getMessage(), HttpStatus.ACCEPTED);
//}