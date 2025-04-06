package com.example.core.service;

import com.example.core.entity.BlackListTokenDto;
import com.example.core.entity.BlackListTokens;
import com.example.core.mapper.BlackListMapper;
import com.example.core.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// это вынесем в папку с общими настрйоками
@Service
@RequiredArgsConstructor
public class BlackListService {

    private final BlackListRepository blackListRepository;
    private final BlackListMapper blackListMapper;

    public void addInBlackList(String token){
        if (checkSizeBlackList()){
            clearBlackList();
        }
        BlackListTokenDto modelDto = new BlackListTokenDto(token);
        BlackListTokens model = blackListMapper.map(modelDto);
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
