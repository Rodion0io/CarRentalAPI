package com.example.core.service;

import com.example.api.dto.*;
import com.example.auth.service.JwtService;
import com.example.core.entity.User;
import com.example.core.entity.UserRoles;
import com.example.core.mapper.UserMapper;
import com.example.core.mapper.UserRolesMapper;
import com.example.core.repository.RolesRepository;
import com.example.core.repository.UserRepository;
import com.example.core.constants.Messages;
import com.example.core.repository.UserRolesRepository;
import com.example.exception.ExceptionType;

import com.example.exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRolesMapper userRolesMapper;
    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    private final RolesRepository rolesRepository;
    private final ValidService validationServ;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Transactional
    public RegistrationDto register(RegistrationRequestDto registrationModel){
        if (validationServ.isUniqueEmailAndLogin(registrationModel.login(), registrationModel.email())){
            throw new CustomException("Login is already exists", ExceptionType.ALREADY_EXIST);
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
        if (validationServ.isLogin(loginRequestModel.login())
                && validationServ.correctPasswordCheck(loginRequestModel.login(), loginRequestModel.password())){
            UUID userId = userRepository.findIdByLogin(loginRequestModel.login()).getId();
            List<String> userRoles = userRolesRepository.findRoleNamesByUserId(userId.toString());
            String accessToken = jwtService.generateAccessToken(userId.toString(), loginRequestModel.login(), userRoles);
            String refreshToken = jwtService.generateRefreshToken(userId.toString(), loginRequestModel.login());
            return new LoginDto(accessToken, refreshToken);
        }
        else{
            throw new CustomException(Messages.INCORRECT_LOGIN_OR_PASSWORD, ExceptionType.NOT_FOUND);
        }
    }

    @Transactional
    public UserProfileDto getPersonalProfile(String token) {
        UUID userId = jwtService.extractUserId(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userMapper.map(user);
    }

    @Transactional
    public List<UserRolesDto> getUserRoles(String token){
        UUID userId = jwtService.extractUserId(token);
        List<UserRolesDto> userRoles = new ArrayList<>();
        List<String> userRolesIdList = userRolesRepository.findRoleIdByUserId(userId.toString());
        List<String> userRolesNameList = userRolesRepository.findRoleNamesByUserId(userId.toString());

        for (int i = 0; i < userRolesIdList.size(); i++){
            UserRolesDto roleModel = new UserRolesDto(userRolesIdList.get(i), userRolesNameList.get(i));
            userRoles.add(roleModel);
        }

        return userRoles;
    }

    @Transactional
    public List<UserProfileDto> getUsersList(List<UUID> usersId){
        List<UserProfileDto> usersList = new ArrayList<>();
        for (int i = 0; i < usersId.size(); i++){
            User user = userRepository.findById(usersId.get(i))
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            UserProfileDto userDto = userMapper.map(user);
            usersList.add(userDto);
        }
        return usersList;
    }

    @Transactional
    public ResponseDto updateProfile(UserUpdateDto updateModel, String token){
        if (validationServ.isUniqueEmailAndLogin(updateModel.login(), updateModel.email())){
            throw new CustomException(Messages.ALREADY_EXISTS, ExceptionType.ALREADY_EXIST);
        }
        else{
            UUID userId = jwtService.extractUserId(token);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            User updatedUser = userMapper.map(user, updateModel, passwordEncoder.encode(updateModel.password()));
            userRepository.save(updatedUser);
            return new ResponseDto(200,"Updated");
        }
    }

    @Transactional
    public ResponseDto AddRole(RoleDto model, UUID userId){
        // Проверить существование роли, наличие у пользователя выбранной роли
        rolesRepository.findById(model.roleId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<String> userRoles = userRolesRepository.findRoleIdByUserId(userId.toString());
        if (!userRoles.contains(model.roleId())){
            UserRolesDto firstRoles = new UserRolesDto(userId.toString(), model.roleId().toString());
            UserRoles roles = userRolesMapper.map(firstRoles);
            userRolesRepository.save(roles);
            return new ResponseDto(200, "Added new role");
        }
        else{
            return new ResponseDto(401, "The user already has this role.");
        }
    }

    @Transactional
    public ResponseDto RemoveRole(UUID userId, UUID roleId){
        rolesRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<String> userRoles = userRolesRepository.findRoleIdByUserId(userId.toString());
        System.out.println("user roles list: " + userRoles);
        System.out.println("user role: " + roleId);
        if (userRoles.contains(roleId.toString())){
            UserRoles role = userRolesRepository.findRole(userId.toString(), roleId.toString())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            userRolesRepository.delete(role);
            return new ResponseDto(200, "Removed role");
        }
        else{
            return new ResponseDto(400, "Error");
        }
    }
}