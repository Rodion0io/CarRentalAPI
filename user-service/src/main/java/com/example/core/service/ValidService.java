package com.example.core.service;

import com.example.core.constants.Messages;
import com.example.core.entity.User;
import com.example.core.repository.UserRepository;
import com.example.exception.CustomException;
import com.example.exception.ExceptionType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    void isBlockedOrDeleteAccount(UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.getBlockedAt() != null){
            throw new CustomException(Messages.BLOCKED_ACCOUNT, ExceptionType.FORBIDDEN);
        }
        if (user.getDeletedAt() != null){
            throw new CustomException(Messages.DELETED_ACCOUNT, ExceptionType.FORBIDDEN);
        }
    }
}
