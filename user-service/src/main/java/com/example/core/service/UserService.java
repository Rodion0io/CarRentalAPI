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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final BlackListService blackListService;


    @Transactional
    public RegistrationDto register(RegistrationRequestDto registrationModel){
        validationServ.checkValidDatas(registrationModel.login(),
                registrationModel.password(), registrationModel.name(),
                registrationModel.surname(), registrationModel.middlename(),
                registrationModel.phone(), registrationModel.email());
        if (validationServ.isUniqueEmailAndLogin(registrationModel.login(), registrationModel.email())){
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
        validationServ.checkValidDatas(loginRequestModel.login(),
                loginRequestModel.password(), null, null,
                null, null, null);
        if (validationServ.isLogin(loginRequestModel.login())
                && validationServ.correctPasswordCheck(loginRequestModel.login(), loginRequestModel.password())){
            UUID userId = userRepository.findIdByLogin(loginRequestModel.login()).getId();
            List<String> userRoles = userRolesRepository.findRoleNamesByUserId(userId.toString());
            String accessToken = jwtService.generateAccessToken(userId.toString(), loginRequestModel.login(), userRoles);
            if (blackListService.inBlackList(accessToken)){
                throw new CustomException(Messages.INCORRECT_TOKEN, ExceptionType.UNAUTHORIZED);
            }
            String refreshToken = jwtService.generateRefreshToken(userId.toString(), loginRequestModel.login());
            return new LoginDto(accessToken, refreshToken);
        }
        else{
            throw new CustomException(Messages.INCORRECT_LOGIN_OR_PASSWORD, ExceptionType.NOT_FOUND);
        }
    }

    //ок
    @Transactional
    public UserProfileDto getPersonalProfile(String token) {
        UUID userId = jwtService.extractUserId(token, true);
        validationServ.isBlockedOrDeleteAccount(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userMapper.map(user);
    }

    //ок
    @Transactional
    public List<UserRolesDto> getUserRoles(String token){
        UUID userId = jwtService.extractUserId(token, true);
        validationServ.isBlockedOrDeleteAccount(userId);
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
    public List<UserProfileDto> getUsersList(List<UUID> usersId, String token){
        UUID currentUserId = jwtService.extractUserId(token, true);
        validationServ.isBlockedOrDeleteAccount(currentUserId);
        List<UserProfileDto> usersList = new ArrayList<>();
        for (int i = 0; i < usersId.size(); i++){
            Optional<User> optionalUser = userRepository.findById(usersId.get(i));
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                UserProfileDto userDto = userMapper.map(user);
                usersList.add(userDto);
            }
        }
        return usersList;
    }

    // ok
    @Transactional
    public ResponseDto updateProfile(UserUpdateDto updateModel, String token){
        UUID userId = jwtService.extractUserId(token, true);
        validationServ.isBlockedOrDeleteAccount(userId);
        validationServ.checkValidDatas(updateModel.login(),
                updateModel.password(), updateModel.name(), updateModel.surname(),
                updateModel.middlename(), updateModel.phone(), updateModel.email());
        if (validationServ.isUniqueEmailAndLogin(updateModel.login(), updateModel.email())){
            throw new CustomException(Messages.ALREADY_EXISTS, ExceptionType.ALREADY_EXIST);
        }
        else{
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            User updatedUser = userMapper.map(user, updateModel, passwordEncoder.encode(updateModel.password()));
            userRepository.save(updatedUser);
            return new ResponseDto(200,"Updated");
        }
    }

    //ok
    @Transactional
    public ResponseDto AddRole(RoleDto model, UUID userId, String token){
        UUID currentUserId = jwtService.extractUserId(token, true);
        validationServ.isBlockedOrDeleteAccount(currentUserId);
        // Проверить существование роли, наличие у пользователя выбранной роли
        rolesRepository.findById(model.roleId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<String> userRoles = userRolesRepository.findRoleIdByUserId(userId.toString());
        if (!userRoles.contains(model.roleId())){
            if (rolesRepository.findById(model.roleId()).isPresent()){
                UserRolesDto firstRoles = new UserRolesDto(userId.toString(), model.roleId().toString());
                UserRoles roles = userRolesMapper.map(firstRoles);
                userRolesRepository.save(roles);
                return new ResponseDto(200, "Added new role");
            }
            else{
                throw new CustomException(Messages.NOT_FOUND_ROLE, ExceptionType.BAD_REQUEST);
            }
        }
        else{
            return new ResponseDto(401, "The user already has this role.");
        }
    }

    //ok
    @Transactional
    public ResponseDto RemoveRole(UUID userId, UUID roleId, String token){
        UUID currentUserId = jwtService.extractUserId(token, true);
        validationServ.isBlockedOrDeleteAccount(currentUserId);
        rolesRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<String> userRoles = userRolesRepository.findRoleIdByUserId(userId.toString());
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

    @Transactional
    public ResponseDto Logout(String token){
        blackListService.addInBlackList(token);
        return new ResponseDto(200, "Is logout");
    }

    @Transactional
    public LoginDto RefreshToken(String refresh){
        if (!jwtService.isTokenExpired(refresh, false)){
            if (!blackListService.inBlackList(refresh)){
                UUID userId = jwtService.extractUserId(refresh, false);
                String login = jwtService.extractLogin(refresh, false);

                List<String> userRoles = userRolesRepository.findRoleNamesByUserId(userId.toString());
                String accessToken = jwtService.generateAccessToken(userId.toString(), login, userRoles);
                if (blackListService.inBlackList(accessToken)){
                    throw new CustomException(Messages.INCORRECT_TOKEN, ExceptionType.UNAUTHORIZED);
                }
                blackListService.addInBlackList(refresh);
                String refreshToken = jwtService.generateRefreshToken(userId.toString(), login);
                if (blackListService.inBlackList(refreshToken)){
                    throw new CustomException(Messages.INCORRECT_TOKEN, ExceptionType.UNAUTHORIZED);
                }
                return new LoginDto(accessToken, refreshToken);
            }
            else{
                throw new CustomException(Messages.INVALID_REFRESH, ExceptionType.UNAUTHORIZED);
            }
        }
        else{
            throw new CustomException(Messages.NOT_EXPIRED_TOKEN, ExceptionType.UNAUTHORIZED);
        }
    }


    //ok
    @Transactional
    public ResponseDto BlockUser(String userId, String token){
        UUID currentUserId = jwtService.extractUserId(token, true);
        validationServ.isBlockedOrDeleteAccount(currentUserId);
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new CustomException("User is not found", ExceptionType.NOT_FOUND));
        if (user.getBlockedAt() != null){
            throw new CustomException("User is blocked", ExceptionType.BAD_REQUEST);
        }
        user.setBlockedAt(LocalDateTime.now());
        userRepository.save(user);
        return new ResponseDto(200, "Is blocked!");
    }

    //ok
    @Transactional
    public ResponseDto UnBlockUser(String userId, String token){
        // !!!!!!!!
        UUID currentUserId = jwtService.extractUserId(token, true);
        validationServ.isBlockedOrDeleteAccount(currentUserId);
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new CustomException("User is not found", ExceptionType.NOT_FOUND));
        if (user.getBlockedAt() == null){
            throw new CustomException("User isn't block", ExceptionType.BAD_REQUEST);
        }
        user.setBlockedAt(null);
        userRepository.save(user);
        return new ResponseDto(200, "Is unblocked");
    }

    @Transactional
    public ResponseDto DeleteAccount(String token){
        UUID userId = jwtService.extractUserId(token, true);
        validationServ.isBlockedOrDeleteAccount(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User is not found", ExceptionType.NOT_FOUND));
        if (user.getDeletedAt() != null){
            throw new CustomException("Account is deleted", ExceptionType.BAD_REQUEST);
        }
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return new ResponseDto(200, "Is deleted! You can restore your account");
    }

    @Transactional
    public ResponseDto RecoverAccount(String token){
        UUID userId = jwtService.extractUserId(token, true);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User is not found", ExceptionType.NOT_FOUND));
        if (user.getDeletedAt() == null){
            throw new CustomException("Account isn't delete", ExceptionType.BAD_REQUEST);
        }
        user.setDeletedAt(null);
        userRepository.save(user);
        return new ResponseDto(200, "Your account has been restored!");
    }
}