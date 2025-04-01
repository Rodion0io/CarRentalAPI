package com.example.api.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import com.example.core.constants.RegularExpressions;
import com.example.core.constants.Messages;

@Builder
public record RegistrationRequestDto(

    @Size(min = 2, max = 30, message = Messages.PERSONAL_DATA_INVALID)
    String name,
    String middlename,
    String surname,

    @Pattern(regexp = RegularExpressions.EMAIL_PATTERN, message = Messages.EMAIL_INVALID)
    String email,
    @Pattern(regexp = RegularExpressions.LOGIN_PATTERN, message = Messages.LOGIN_INVALID)
    String login,
    @Pattern(regexp = RegularExpressions.PASSWORD_PATTERN, message = Messages.PASSWORD_INVALID)
    String password,
    @Pattern(regexp = RegularExpressions.PHONE_PATTERN, message = Messages.PHONE_INVALID)
    String phone
) {
}
