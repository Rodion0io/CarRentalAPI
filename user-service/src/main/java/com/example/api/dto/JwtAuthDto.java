package com.example.api.dto;

import lombok.Builder;

@Builder
public record JwtAuthDto(
        String accessToken,
        String refreshToken
) {
}
