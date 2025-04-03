package com.example.api.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiPaths {

    private static final String partLink = "/api/user/";

    public static final String LOGIN = partLink + "login";

    public static final String LOGOUT = partLink + "logout";

    public static final String REGISTRATION = partLink + "registration";

    public static final String PERSONAL_PROFILE = partLink + "myprofile";
}
