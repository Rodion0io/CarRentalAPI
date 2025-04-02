package com.example.core.entity;

import lombok.Builder;

@Builder
public record ResponseModel(
        Number statusCode,
        String message
) {
}
