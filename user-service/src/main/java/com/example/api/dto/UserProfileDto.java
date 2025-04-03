package com.example.api.dto;

import io.micrometer.common.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserProfileDto(
        UUID id,
        String name,
        String surname,
        @Nullable
        String middlename,
        String email,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean isBlocked,
        LocalDateTime blockedAt,
        LocalDateTime deletedAt
) {
}
