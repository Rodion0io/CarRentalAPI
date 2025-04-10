package com.example.api.rest;

import com.example.api.constant.ApiPaths;
import com.example.api.dto.*;
import com.example.core.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //userService
    @PostMapping(ApiPaths.REGISTRATION)
    public RegistrationDto Registration(@RequestBody RegistrationRequestDto registModel){
        return userService.register(registModel);
    }

    //userService
    @PostMapping(ApiPaths.LOGIN)
    public LoginDto Login(@RequestBody LoginRequestDto loginRequestModel){
        return userService.logIn(loginRequestModel);
    }

    //userService
    @GetMapping(ApiPaths.PERSONAL_PROFILE)
    public UserProfileDto PersonalProfile(@RequestHeader("Authorization") String authHeader){
        return userService.getPersonalProfile(authHeader.substring(7));
    }

    //userService
    @GetMapping(ApiPaths.USER_ROLES_PATH)
    public List<UserRolesDto> UserRoles(@RequestHeader("Authorization") String authHeader){
        return userService.getUserRoles(authHeader.substring(7));
    }

    //userService
    @PutMapping(ApiPaths.UPDATE_PROFILE)
    public ResponseDto UpdateProfile(@RequestBody UserUpdateDto updateModel, @RequestHeader("Authorization") String authHeader){
        return userService.updateProfile(updateModel, authHeader.substring(7));
    }

    //userService
    @PutMapping(ApiPaths.DELETE_ACCOUNT)
    public ResponseDto DeleteAccount(@RequestHeader("Authorization") String authHeader){
        return userService.DeleteAccount(authHeader.substring(7));
    }

    //userService
    @PutMapping(ApiPaths.RECOVER_ACCOUNT)
    public ResponseDto RecoverAccount(@RequestHeader("Authorization") String authHeader){
        return userService.RecoverAccount(authHeader.substring(7));
    }

    //userService
    @PostMapping(ApiPaths.LOGOUT)
    public ResponseDto Logout(@RequestHeader("Authorization") String authHeader){
        return userService.Logout(authHeader.substring(7));
    }

    //userService
    @PostMapping(ApiPaths.REFRESH)
    public LoginDto Refresh(@RequestBody RefreshModel model){
        return userService.RefreshToken(model.refreshToken());
    }
}
