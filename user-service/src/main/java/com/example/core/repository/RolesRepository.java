package com.example.core.repository;

import com.example.core.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Roles, UUID> {

    Optional<Roles> findById(UUID id);
}
