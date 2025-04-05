package com.example.api.dto;

import com.example.core.constants.Messages;
import com.example.core.constants.RegularExpressions;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(
        @Size(min = 2, max = 30, message = Messages.PERSONAL_DATA_INVALID)
        String name,

        @Size(min = 2, max = 30, message = Messages.PERSONAL_DATA_INVALID)
        String surname,

        @Size(min = 2, max = 30, message = Messages.PERSONAL_DATA_INVALID)
        @Nullable
        String middlename,

        @Pattern(regexp = RegularExpressions.EMAIL_PATTERN, message = Messages.EMAIL_INVALID)
        String email,

        @Pattern(regexp = RegularExpressions.PHONE_PATTERN, message = Messages.PHONE_INVALID)
        String phone,

        @Pattern(regexp = RegularExpressions.LOGIN_PATTERN, message = Messages.LOGIN_INVALID)
        String login,

        @Pattern(regexp = RegularExpressions.PASSWORD_PATTERN, message = Messages.PASSWORD_INVALID)
        String password
) {
}
