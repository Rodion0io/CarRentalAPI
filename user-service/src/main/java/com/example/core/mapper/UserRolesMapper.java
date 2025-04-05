package com.example.core.mapper;

import com.example.api.dto.UserRolesDto;
import com.example.core.entity.UserRoles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRolesMapper {
    UserRolesMapper INSTANCE = Mappers.getMapper(UserRolesMapper.class);

    @Mapping(source = "userId", target = "user_id")
    @Mapping(source = "roleId", target = "role_id")
    UserRoles map(UserRolesDto userRolesDto);


}
