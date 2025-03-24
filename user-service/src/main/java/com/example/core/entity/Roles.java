package com.example.core.entity;

import org.hibernate.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.UUID;


@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    private UUID id;

    @Column(name="roleName", length = 100, nullable = false)
    private String roleName;
}
