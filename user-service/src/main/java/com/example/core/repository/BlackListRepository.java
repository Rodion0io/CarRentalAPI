//package com.example.core.repository;
//
//import com.example.core.entity.BlackListTokens;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//import java.util.UUID;
//
//
//// это вынесем в папку с общими настрйоками
//public interface BlackListRepository extends JpaRepository<BlackListTokens, UUID> {
//    Optional<BlackListTokens> findByToken(String token);
//}
