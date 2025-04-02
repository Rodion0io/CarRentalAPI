package com.example.api.dto;

public record LoginDto(
        String accessToken,
        String refreshToken
) {
}
