package com.example.core.repository;

import com.example.core.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, UUID> {

    @Query("SELECT r.role_name FROM Roles r WHERE CAST(r.id AS string) IN " +
            "(SELECT ur.role_id FROM UserRoles ur WHERE ur.user_id = :user_id)")
    List<String> findRoleNamesByUserId(@Param("user_id") String userId);

    @Query("SELECT r.id FROM Roles r WHERE CAST(r.id AS string) IN " +
            "(SELECT ur.role_id FROM UserRoles ur WHERE ur.user_id = :user_id)")
    List<String> findRoleIdByUserId(@Param("user_id") String userId);

    @Query("SELECT ur FROM UserRoles ur WHERE ur.user_id = :user_id AND ur.role_id = :role_id")
    Optional<UserRoles> findRole(@Param("user_id") String userId, @Param("role_id") String roleId);
}