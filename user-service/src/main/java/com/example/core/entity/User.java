package com.example.core.entity;

import lombok.*;
//import javax.persistence.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name="name", length = 100, nullable = false)
    private String name;

    @Column(name="middlename", length = 100, nullable = true)
    private String middlename;

    @Column(name="surname", length = 100, nullable = false)
    private String surname;

    @Column(name="email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name="login", length = 100, nullable = false, unique = true)
    private String login;

    @Column(name="password", length = 100, nullable = false)
    private String password;

    @Column(name="phone", length = 12, nullable = false)
    private String phone;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime blockedAt;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
