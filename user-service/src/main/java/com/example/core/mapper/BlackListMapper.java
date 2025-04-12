//package com.example.core.mapper;
//
//import com.example.core.entity.BlackListTokens;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//// это вынесем в папку с общими настрйоками
//@Mapper
//public interface BlackListMapper {
//    BlackListMapper INSTANCE = Mappers.getMapper(BlackListMapper.class);
//
//    @Mapping(target = "token", source = "token")
//    BlackListTokens map(BlackListTokenDto blackListTokenDto);
//}
