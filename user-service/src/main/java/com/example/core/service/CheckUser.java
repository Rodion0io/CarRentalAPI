package com.example.core.service;

import com.example.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckUser {
    private final UserRepository userRepository;

    boolean isUniqueEmailAndLogin(String login, String email){
        if (userRepository.existsByLoginAndEmail(login, email)){
            return true;
        }
        return false;
    }
}
