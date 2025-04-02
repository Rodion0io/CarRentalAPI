package com.example.core.service;

import com.example.api.dto.RegistrationDto;
import com.example.api.dto.RegistrationRequestDto;
import com.example.api.dto.UserRolesDto;
import com.example.core.entity.User;
import com.example.core.entity.UserRoles;
import com.example.core.mapper.UserMapper;
import com.example.core.mapper.UserRolesMapper;
import com.example.core.repository.UserRepository;
import com.example.core.constants.Messages;
import com.example.core.repository.UserRolesRepository;
import com.example.exception.ExceptionType;

import com.example.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRolesMapper userRolesMapper;
    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    private final CheckUser checkUser;
    private final BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public RegistrationDto register(RegistrationRequestDto registrationModel){
        if (checkUser.isUniqueEmailAndLogin(registrationModel.login(), registrationModel.email())){
            throw new CustomException(Messages.ALREADY_EXISTS, ExceptionType.ALREADY_EXIST);
        }
        else{
            User user = userMapper.map(registrationModel, passwordEncoder.encode(registrationModel.password()));
            User savedUser = userRepository.save(user);
            UUID userId = savedUser.getId();
            UserRolesDto firstRoles = new UserRolesDto(userId.toString(), "8bfcb2d4-c8ed-43e8-86aa-04bfba54bf6f");
            UserRoles roles = userRolesMapper.map(firstRoles);
            userRolesRepository.save(roles);
            return new RegistrationDto("Account has registered");
        }
    }


}
