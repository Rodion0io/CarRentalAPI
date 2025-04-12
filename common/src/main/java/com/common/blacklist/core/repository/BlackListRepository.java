package com.common.blacklist.core.repository;

import com.common.blacklist.core.entity.BlackListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlackListRepository extends JpaRepository<BlackListEntity, UUID> {
    Optional<BlackListEntity> findByToken(String token);
}
