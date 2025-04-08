package com.example.core.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Messages {
    public static final String EMAIL_INVALID = "Неверные адрес почты (пример: example@mail.ru)";

    public static final String PASSWORD_INVALID = "Минимальная длина пароля 8 символов, включая заглавные, строчные символы, а также спец. символы";

    public static final String PHONE_INVALID = "Неверный номер телефона (пример: +71234567890 или 81234567890)";

    public static final String LOGIN_INVALID = "Логин должен содержать от 5 до 30 символов";

    public static final String BLOCKED_ACCOUNT = "Ваш аккаунт заблокирован";

    public static final String PERSONAL_DATA_INVALID = "Неверный формат персональных данных";

    public static final String ALREADY_EXISTS = "Такой логин или почта уже используется";

    public static final String SUCCESS = "Успех";

    public static final String INCORRECT_LOGIN_OR_PASSWORD = "Неверный логин или пароль";

    public static final String INCORRECT_TOKEN = "Перевыполните запрос, случайно сгенерировался токен, который находится в черном списке";

    public static final String NOT_EXPIRED_TOKEN = "Жизненный цикл токена закончился!";

    public static final String INVALID_REFRESH = "Жизненный цикл refresh токена закончился!";

    public static final String DELETED_ACCOUNT = "Аккаунт удален";
}
