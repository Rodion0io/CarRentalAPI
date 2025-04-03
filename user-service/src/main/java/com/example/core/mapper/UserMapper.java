package com.example.core.mapper;

import com.example.api.dto.RegistrationRequestDto;
import com.example.api.dto.UserProfileDto;
import com.example.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", source = "encode")
    User map(RegistrationRequestDto registrationRequestDto, String encode);

    @Mapping(target = "middlename", source = "middlename", nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    UserProfileDto map(User user);
}
