package com.example.api.rest;

import com.example.api.constant.ApiPaths;

import com.example.api.dto.LoginDto;
import com.example.api.dto.LoginRequestDto;
import com.example.api.dto.RegistrationDto;
import com.example.api.dto.RegistrationRequestDto;
import com.example.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
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

    @PostMapping(ApiPaths.LOGOUT)
    public String logout(){
        return "hello";
    }
}
