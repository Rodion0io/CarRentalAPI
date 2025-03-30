CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    middlename VARCHAR(100),
    surname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(12),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    deleted_at TIMESTAMP,
    blocked_at TIMESTAMP
);

CREATE TABLE roles (
    id UUID PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL
);

CREATE TABLE user_roles (
    id UUID PRIMARY KEY,
    role_id VARCHAR(100) NOT NULL,
    user_id VARCHAR(100) NOT NULL
);