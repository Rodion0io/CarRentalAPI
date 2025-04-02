package com.example.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    ALREADY_EXIST(409);

    private final int code;
}
