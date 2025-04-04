package com.example.api.dto;

import io.micrometer.common.lang.Nullable;

import java.time.LocalDateTime;

public record UserUpdateDto(
        String name,
        String surname,
        @Nullable
        String middlename,
        String email,
        String phone
) {
}
