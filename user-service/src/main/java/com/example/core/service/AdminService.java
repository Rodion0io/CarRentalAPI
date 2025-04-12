package com.example.core.service;

import com.example.api.dto.ResponseDto;
import com.example.api.dto.RoleDto;
import com.example.api.dto.UserProfileDto;
import com.example.api.dto.UserRolesDto;
import com.common.auth.service.JwtService;
import com.example.core.constants.Messages;
import com.example.core.entity.User;
import com.example.core.entity.UserRoles;
import com.example.core.mapper.UserMapper;
import com.example.core.mapper.UserRolesMapper;
import com.example.core.repository.RolesRepository;
import com.example.core.repository.UserRepository;
import com.example.core.repository.UserRolesRepository;
import com.example.exception.CustomException;
import com.example.exception.ExceptionType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserMapper userMapper;
    private final UserRolesMapper userRolesMapper;
    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    private final RolesRepository rolesRepository;
    private final ValidService validationServ;
    private final JwtService jwtService;

    //admin
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

    //ok
    //admin
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
    //admin
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

    //ok
    //admin
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
    //admin
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
}
