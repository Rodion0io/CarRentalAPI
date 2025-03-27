package com.example.core.entity;


import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "user-roles")
public class UserRoles {

    @Column(name="roleId", length = 100, nullable = false)
    private String roleId;

    @Column(name="userId", length = 100, nullable = false)
    private String userId;
}
