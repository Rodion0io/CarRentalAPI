package com.example.api.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiPaths {

    private static final String PART_USER_LINK = "/api/user/";

    public static final String LOGIN = PART_USER_LINK + "login";

    public static final String LOGOUT = PART_USER_LINK + "logout";

    public static final String REGISTRATION = PART_USER_LINK + "registration";

    public static final String PERSONAL_PROFILE = PART_USER_LINK + "myprofile";

    public static final String USER_ROLES_PATH = PART_USER_LINK + "userroles";

    public static final String USERS_LIST = PART_USER_LINK + "userslist";

    public static final String UPDATE_PROFILE = PART_USER_LINK + "update";

}
