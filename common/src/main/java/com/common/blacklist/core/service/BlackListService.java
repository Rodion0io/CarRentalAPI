package com.common.blacklist.core.service;

import com.common.blacklist.core.repository.BlackListRepository;
import com.common.blacklist.core.entity.BlackListEntity;
import com.common.blacklist.core.dto.BlackListDto;

import com.common.blacklist.mapper.BlackListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListService {

    private final BlackListRepository blackListRepository;
    private final BlackListMapper blackListMapper;

    public void addInBlackList(String token){
        if (checkSizeBlackList()){
            clearBlackList();
        }
        BlackListDto modelDto = new BlackListDto(token);
        BlackListEntity model = blackListMapper.map(modelDto);
        blackListRepository.save(model);
    }

    public boolean inBlackList(String token) {
        return blackListRepository.findByToken(token)
                .map(model -> true)
                .orElse(false);
    }

    private boolean checkSizeBlackList(){
        long count = blackListRepository.count();
        if (count == 15){
            return true;
        }
        return false;
    }

    private void clearBlackList(){
        blackListRepository.deleteAll();
    }
}