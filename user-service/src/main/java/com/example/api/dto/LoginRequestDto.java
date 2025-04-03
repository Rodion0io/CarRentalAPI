package com.example.api.dto;

import com.example.core.constants.Messages;
import com.example.core.constants.RegularExpressions;
import jakarta.validation.constraints.Pattern;

public record LoginRequestDto(
        @Pattern(regexp = RegularExpressions.LOGIN_PATTERN, message = Messages.LOGIN_INVALID)
        String login,
        @Pattern(regexp = RegularExpressions.PASSWORD_PATTERN, message = Messages.PASSWORD_INVALID)
        String password
) {
}
