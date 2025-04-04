package com.example.api.rest;

import com.example.api.constant.ApiPaths;

import com.example.api.dto.*;
import com.example.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(ApiPaths.REGISTRATION)
    public RegistrationDto registration(@RequestBody RegistrationRequestDto registModel){
        return userService.register(registModel);
    }

    @PostMapping(ApiPaths.LOGIN)
    public LoginDto login(@RequestBody LoginRequestDto loginRequestModel){
        return userService.logIn(loginRequestModel);
    }

    @GetMapping(ApiPaths.PERSONAL_PROFILE)
    public UserProfileDto personalProfile(@RequestHeader("Authorization") String authHeader){
        return userService.getPersonalProfile(authHeader.substring(7));
    }

    @GetMapping(ApiPaths.USER_ROLES_PATH)
    public List<UserRolesDto> userRoles(@RequestHeader("Authorization") String authHeader){
        return userService.getUserRoles(authHeader.substring(7));
    }

    @GetMapping(ApiPaths.USERS_LIST)
    public List<UserProfileDto> usersProfiles(@RequestParam List<UUID> usersId){
        return userService.getUsersList(usersId);
    }

    @PutMapping(ApiPaths.UPDATE_PROFILE)
    public ResponseDto updateProfile(@RequestBody UserUpdateDto updateModel, @RequestHeader("Authorization") String authHeader){
        return userService.updateProfile(updateModel, authHeader.substring(7));
    }

    @PutMapping(ApiPaths.ADD_ROLE)
    public ResponseDto addRole(@PathVariable("id") UUID userId, @RequestBody RoleDto model){
        return userService.AddRole(model, userId);
    }

    @DeleteMapping(ApiPaths.REMOVE_ROLE)
    public ResponseDto removeRole(@PathVariable("id") UUID userId, @RequestParam("roleId") UUID roleId){
        return userService.RemoveRole(userId, roleId);
    }
}
