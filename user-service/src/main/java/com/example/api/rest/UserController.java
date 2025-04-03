package com.example.api.rest;

import com.example.api.constant.ApiPaths;

import com.example.api.dto.*;
import com.example.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(ApiPaths.PERSONAL_PROFILE)
    public UserProfileDto personalProfile(@RequestHeader("Authorization") String authHeader){
        return userService.getPersonalProfile(authHeader.substring(7));
    }

    @PostMapping(ApiPaths.LOGOUT)
    public String logout(){
        return "hello";
    }
}
