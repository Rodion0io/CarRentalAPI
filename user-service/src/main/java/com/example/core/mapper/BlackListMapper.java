package com.example.core.mapper;

import com.example.api.dto.RegistrationRequestDto;
import com.example.core.entity.BlackListTokenDto;
import com.example.core.entity.BlackListTokens;
import com.example.core.entity.User;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

// это вынесем в папку с общими настрйоками
public interface BlackListMapper {
    BlackListMapper INSTANCE = Mappers.getMapper(BlackListMapper.class);

    @Mapping(target = "token", source = "token")
    BlackListTokens map(BlackListTokenDto blackListTokenDto);
}
