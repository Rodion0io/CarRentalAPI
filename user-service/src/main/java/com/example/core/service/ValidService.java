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

import com.example.core.constants.RegularExpressions;


@Service
@RequiredArgsConstructor
public class ValidService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    boolean isUniqueEmailAndLogin(String login, String email){
        if (userRepository.existsByLogin(login) || userRepository.existsByEmail(email)){
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

//    <T extends RegistrationRequestDto | LoginRequestDto> void checkValidPersonalDatas(T model){
//
//    }

    void checkValidDatas(String login, String password, String name,
                         String surname, String middlename, String phone,
                         String email){
        if (login == null || !login.matches(RegularExpressions.LOGIN_PATTERN)){
            throw new CustomException(Messages.LOGIN_INVALID, ExceptionType.BAD_REQUEST);
        }
        if (password == null || !password.matches(RegularExpressions.PASSWORD_PATTERN)){
            throw new CustomException(Messages.PASSWORD_INVALID, ExceptionType.BAD_REQUEST);
        }
        if (name == null || surname == null
                || !name.matches(RegularExpressions.PERSONAL_DATAS_PATTERN)
                || !surname.matches(RegularExpressions.PERSONAL_DATAS_PATTERN)
                || !middlename.matches(RegularExpressions.PERSONAL_DATAS_PATTERN)){
            throw new CustomException(Messages.NOT_FOUND_NAME, ExceptionType.BAD_REQUEST);
        }
        if (phone == null || !phone.matches(RegularExpressions.PHONE_PATTERN)){
            throw new CustomException(Messages.PHONE_INVALID, ExceptionType.BAD_REQUEST);
        }
        if (email == null || !email.matches(RegularExpressions.EMAIL_PATTERN)){
            throw new CustomException(Messages.EMAIL_INVALID, ExceptionType.BAD_REQUEST);
        }
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
