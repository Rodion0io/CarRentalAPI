package com.example.api.rest;

import com.example.api.constant.ApiPaths;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/registration")
    public String registration(){
        System.out.println("🚀 Контроллер получил запрос!");
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
