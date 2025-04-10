package com.example.api.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiPaths {

    private static final String PART_USER_LINK = "/api/user/";

    private static final String PART_ADMIN_LINK = "/api/admin/";

    public static final String LOGIN = PART_USER_LINK + "login";

    public static final String LOGOUT = PART_USER_LINK + "logout";

    public static final String REGISTRATION = PART_USER_LINK + "registration";

    public static final String PERSONAL_PROFILE = PART_USER_LINK + "myprofile";

    public static final String USER_ROLES_PATH = PART_USER_LINK + "userroles";

    public static final String USERS_LIST = PART_ADMIN_LINK + "userslist";

    public static final String UPDATE_PROFILE = PART_USER_LINK + "update";

    public static final String ADD_ROLE = PART_ADMIN_LINK + "newRole/{id}";

    public static final String REMOVE_ROLE = PART_ADMIN_LINK + "deleteRole/{id}";

    public static final String REFRESH = PART_USER_LINK + "refresh";

    public static final String BLOCK = PART_ADMIN_LINK + "block/{id}";

    public static final String UNBLOCK = PART_ADMIN_LINK + "unblock/{id}";

    public static final String DELETE_ACCOUNT = PART_USER_LINK + "deleteAccount";

    public static final String RECOVER_ACCOUNT = PART_USER_LINK + "recoverAccount";
}
