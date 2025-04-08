package com.example.core.repository;

import com.example.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLogin(String login);

    Optional<User> findById(UUID id);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    User findPasswordByLogin(String login);

    User findIdByLogin(String login);


}