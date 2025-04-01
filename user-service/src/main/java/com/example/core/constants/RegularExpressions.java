package com.example.core.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegularExpressions {
    public static final String EMAIL_PATTERN = "^[a-z\\d][a-z\\d\\-\\_\\.]{3,}\\@[a-z\\-\\_\\.\\d]{3,}\\.(com|ru|net|org)$";

    public static final String PASSWORD_PATTERN = "^[A-Za-z\\d!@#\\$\\%\\:\\;\\^\\,\\.\\*\\)\\(\\-\\_\\=\\+\\`\\~]{8,}$";

    public static final String PHONE_PATTERN = "^(\\+(7))||8\\d{10}$";

    public static final String LOGIN_PATTERN = "[A-Za-z\\d]{4,}";
}
