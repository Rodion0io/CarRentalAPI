package com.example.api.rest;

import com.example.api.constant.ApiPaths;
import com.example.api.dto.ResponseDto;
import com.example.api.dto.RoleDto;
import com.example.api.dto.UserProfileDto;
import com.example.core.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    //admin?
    @GetMapping(ApiPaths.USERS_LIST)
    public List<UserProfileDto> UsersProfiles(@RequestParam List<UUID> usersId, @RequestHeader("Authorization") String authHeader){
        return adminService.getUsersList(usersId, authHeader.substring(7));
    }

    //admin
    @PutMapping(ApiPaths.ADD_ROLE)
    public ResponseDto AddRole(@PathVariable("id") UUID userId, @RequestBody RoleDto model, @RequestHeader("Authorization") String authHeader){
        return adminService.AddRole(model, userId, authHeader.substring(7));
    }

    //admin
    @DeleteMapping(ApiPaths.REMOVE_ROLE)
    public ResponseDto RemoveRole(@PathVariable("id") UUID userId, @RequestParam("roleId") UUID roleId, @RequestHeader("Authorization") String authHeader){
        return adminService.RemoveRole(userId, roleId, authHeader.substring(7));
    }

    //admin
    @PutMapping(ApiPaths.BLOCK)
    public ResponseDto BlockUser(@PathVariable("id") UUID userId, @RequestHeader("Authorization") String authHeader){
        return adminService.BlockUser(userId.toString(), authHeader.substring(7));
    }

    //admin
    @PutMapping(ApiPaths.UNBLOCK)
    public ResponseDto UnBlockUser(@PathVariable("id") UUID userId, @RequestHeader("Authorization") String authHeader){
        return adminService.UnBlockUser(userId.toString(), authHeader.substring(7));
    }
}
