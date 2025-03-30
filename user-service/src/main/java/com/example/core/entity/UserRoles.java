package com.example.core.entity;


import lombok.*;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "user_roles")
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="role_id", length = 100, nullable = false)
    private String role_id;

    @Column(name="user_id", length = 100, nullable = false)
    private String user_id;
}
