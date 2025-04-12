package com.common.blacklist.mapper;

import com.common.blacklist.core.dto.BlackListDto;
import com.common.blacklist.core.entity.BlackListEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlackListMapper {
    BlackListEntity INSTANCE = Mappers.getMapper(BlackListEntity.class);

    @Mapping(target = "token", source = "token")
    BlackListEntity map(BlackListDto blackListTokenDto);
}
