package com.example.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    NOT_FOUND(404),
    ALREADY_EXIST(409),
    ILLEGAL(400),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    FATAL(500);

    private final int code;
}
