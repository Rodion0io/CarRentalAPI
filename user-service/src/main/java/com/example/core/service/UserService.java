package com.example.core.service;

import com.example.api.dto.*;
import com.example.auth.service.JwtService;
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

import java.util.List;
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
    private final JwtService jwtService;


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

    @Transactional
    public LoginDto logIn(LoginRequestDto loginRequestModel){
        if (checkUser.isLogin(loginRequestModel.login())
                && checkUser.correctPasswordCheck(loginRequestModel.login(), loginRequestModel.password())){
            UUID userId = userRepository.findIdByLogin(loginRequestModel.login());
            List<String> userRoles = userRolesRepository.findRoleNamesByUserId(userId.toString());
            String accessToken = jwtService.generateAccessToken(userId.toString(), loginRequestModel.login(), userRoles);
            String refreshToken = jwtService.generateRefreshToken(userId.toString(), loginRequestModel.login());
            return new LoginDto(accessToken, refreshToken);
        }
        else{
            throw new CustomException(Messages.INCORRECT_LOGIN_OR_PASSWORD, ExceptionType.NOT_FOUND);
        }
    }



}
