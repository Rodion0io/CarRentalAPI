package com.example.core.mapper;

import com.example.api.dto.RegistrationRequestDto;
import com.example.api.dto.UserProfileDto;
import com.example.api.dto.UserUpdateDto;
import com.example.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", source = "encode")
    User map(RegistrationRequestDto registrationRequestDto, String encode);

    @Mapping(target = "name", source = "updateDto.name", nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "surname", source = "updateDto.surname", nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "middlename", source = "updateDto.middlename", nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", source = "updateDto.email", nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "phone", source = "updateDto.phone", nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", source = "encode", nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    User map(@MappingTarget User user, UserUpdateDto updateDto, String encode);

    @Mapping(target = "middlename", source = "middlename", nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    UserProfileDto map(User user);


}
