package com.example.core.entity;

import lombok.*;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name="role_name", length = 100, nullable = false)
    private String role_name;
}
