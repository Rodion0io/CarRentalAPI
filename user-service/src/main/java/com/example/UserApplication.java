package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = {
        "com.example.core.repository",
        "com.common.blacklist.core.repository"
})
@EntityScan(basePackages = {
        "com.example.core.entity",
        "com.common.blacklist.core.entity"
})
@ConfigurationPropertiesScan("com.example")
@SpringBootApplication(scanBasePackages={"com.example", "com.common", "com.common.blacklist.core.repository"})
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}