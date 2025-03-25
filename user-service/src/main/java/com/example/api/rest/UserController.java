package com.example.api.rest;

import com.example.api.constant.ApiPaths;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @PostMapping(ApiPaths.REGISTRATION)
    public String registration(){
        return "hello";
    }

    @PostMapping(ApiPaths.LOGIN)
    public String login(){
        return "hello";
    }

    @PostMapping(ApiPaths.LOGOUT)
    public String logout(){
        return "hello";
    }
}
