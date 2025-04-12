package com.example.config;

//import com.common.blacklist.mapper.BlackListMapper;
import com.example.core.mapper.UserMapper;
import com.example.core.mapper.UserRolesMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public UserMapper userMapper(){
        return UserMapper.INSTANCE;
    }

    @Bean
    public UserRolesMapper userRolesMapper(){
        return UserRolesMapper.INSTANCE;
    }

//    @Bean
//    public BlackListMapper blackListMapper(){
//        return BlackListMapper.INSTANCE;
//    }
}
