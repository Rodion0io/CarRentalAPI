package com.example.api.rest;

import com.example.api.constant.ApiPaths;

import com.example.api.dto.*;
import com.example.core.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    public RegistrationDto Registration(@RequestBody RegistrationRequestDto registModel){
        return userService.register(registModel);
    }

    @PostMapping(ApiPaths.LOGIN)
    public LoginDto Login(@RequestBody LoginRequestDto loginRequestModel){
        return userService.logIn(loginRequestModel);
    }

    @GetMapping(ApiPaths.PERSONAL_PROFILE)
    public UserProfileDto PersonalProfile(@RequestHeader("Authorization") String authHeader){
        return userService.getPersonalProfile(authHeader.substring(7));
    }

    @GetMapping(ApiPaths.USER_ROLES_PATH)
    public List<UserRolesDto> UserRoles(@RequestHeader("Authorization") String authHeader){
        return userService.getUserRoles(authHeader.substring(7));
    }

    @GetMapping(ApiPaths.USERS_LIST)
    public List<UserProfileDto> UsersProfiles(@RequestParam List<UUID> usersId){
        return userService.getUsersList(usersId);
    }

    @PutMapping(ApiPaths.UPDATE_PROFILE)
    public ResponseDto UpdateProfile(@RequestBody UserUpdateDto updateModel, @RequestHeader("Authorization") String authHeader){
        return userService.updateProfile(updateModel, authHeader.substring(7));
    }

    @PutMapping(ApiPaths.ADD_ROLE)
    public ResponseDto AddRole(@PathVariable("id") UUID userId, @RequestBody RoleDto model){
        return userService.AddRole(model, userId);
    }

    @DeleteMapping(ApiPaths.REMOVE_ROLE)
    public ResponseDto RemoveRole(@PathVariable("id") UUID userId, @RequestParam("roleId") UUID roleId){
        return userService.RemoveRole(userId, roleId);
    }

    @PostMapping(ApiPaths.LOGOUT)
    public ResponseDto Logout(@RequestHeader("Authorization") String authHeader){
        return userService.Logout(authHeader.substring(7));
    }
}
