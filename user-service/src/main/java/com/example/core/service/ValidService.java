package com.example.core.service;

import com.example.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    boolean isUniqueEmailAndLogin(String login, String email){
        if (userRepository.existsByLoginAndEmail(login, email)){
            return true;
        }
        return false;
    }

    boolean isLogin(String login){
        if (userRepository.existsByLogin(login)){
            return true;
        }
        return false;
    }

    boolean correctPasswordCheck(String login, String enteredPassword){
        String password = userRepository.findPasswordByLogin(login).getPassword();
        if (passwordEncoder.matches(enteredPassword, password)){
            return true;
        }
        return false;
    }

}
