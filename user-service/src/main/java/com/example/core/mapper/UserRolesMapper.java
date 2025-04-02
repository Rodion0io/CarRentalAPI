package com.example.core.mapper;

import com.example.api.dto.RegistrationRequestDto;
import com.example.api.dto.UserRolesDto;
import com.example.core.entity.User;
import com.example.core.entity.UserRoles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRolesMapper {
    UserRolesMapper INSTANCE = Mappers.getMapper(UserRolesMapper.class);

    UserRoles map(UserRolesDto userRolesDto);
}
