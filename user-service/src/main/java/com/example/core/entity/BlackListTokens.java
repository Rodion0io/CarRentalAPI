package com.example.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "black_list")
public class BlackListTokens {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name="token", length = 400, nullable = false)
    private String token;

}
